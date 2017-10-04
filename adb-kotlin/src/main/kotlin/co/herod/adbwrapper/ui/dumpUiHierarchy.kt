package co.herod.adbwrapper.ui

import co.herod.adbwrapper.exceptions.UiAutomatorBridgeUnavailableException
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.ui.dump.compatDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.fallbackDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.primaryDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.rpcDumpUiHierarchy
import co.herod.adbwrapper.uiautomator.uiAutomatorBridge
import co.herod.kotlin.log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

private val s3: String by lazy { "dumpUiHierarchy" }
private val c = '<'
private val c1 = '>'

fun AdbDevice.dumpUiHierarchy(
        timeout: Long = 30,
        timeUnit: TimeUnit = TimeUnit.SECONDS
): Observable<UiHierarchy> = with(this) {
    Observable.just(this)
            .doOnSubscribe { log("START $s3") }
            .doOnNext { log("NEXT $s3") }
            .doOnComplete { log("COMPLETE $s3") }
            .doOnDispose { log("STOP $s3") }
            .flatMap {
                tryRpc()
                        .doOnError { println(it) }
                        .onErrorResumeNext { _: Throwable -> compatDumpUiHierarchy() }
                        .onErrorResumeNext { _: Throwable -> primaryDumpUiHierarchy() }
                        .onErrorResumeNext { _: Throwable -> fallbackDumpUiHierarchy() }
            }
            .retry()
            .observeOn(Schedulers.single())
            .subscribeOn(Schedulers.computation())
            .filter { it.isNotBlank() }
            .timeout(maxOf(10, timeUnit.toSeconds(timeout) / 5), TimeUnit.SECONDS)
            .retry()
            .timeout(timeout, timeUnit)
            .map { "${it.substringBefore(c).substringBeforeLast(c1)}>" }
            .map { UiHierarchy(this, it) }
}

private fun AdbDevice.tryRpc(): Observable<String> {
    if (uiAutomatorBridge().blockingFirst()) {
        return rpcDumpUiHierarchy()
    }
    return Observable.error(UiAutomatorBridgeUnavailableException())
}

