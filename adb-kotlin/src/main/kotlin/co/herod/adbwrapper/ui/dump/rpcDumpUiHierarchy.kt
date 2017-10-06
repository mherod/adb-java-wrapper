package co.herod.adbwrapper.ui.dump

import co.herod.adbwrapper.device.rpcSession
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.uiautomator.RpcSession
import co.herod.kotlin.ext.retryWithTimeout
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

fun AdbDevice.rpcDumpUiHierarchy(): Observable<String> = RpcSession
        .dumpWindowHierarchy(rpcSession())
        .toObservable()
        .doOnError { println("Error: rpcDumpUiHierarchy: $it") }
        .retryWithTimeout(1800, TimeUnit.MILLISECONDS)

