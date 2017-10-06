package co.herod.adbwrapper.ui

import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.ui.dump.compatDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.fallbackDumpUiHierarchy
import co.herod.adbwrapper.ui.dump.primaryDumpUiHierarchy
import io.reactivex.Observable

fun AdbDevice.singleInternalDumpUiHierarchyAttempt(): Observable<String>? =
        tryRpcDumpUiHierarchy()
                .onErrorResumeNext { _: Throwable -> compatDumpUiHierarchy() }
                .onErrorResumeNext { _: Throwable -> primaryDumpUiHierarchy() }
                .onErrorResumeNext { _: Throwable -> fallbackDumpUiHierarchy() }
                .doOnNext { println("singleDumpUi -> $it") }