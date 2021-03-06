@file:Suppress("unused")

package co.herod.adbwrapper.ui

import co.herod.adbwrapper.AdbBusManager
import co.herod.adbwrapper.model.UiHierarchy
import co.herod.adbwrapper.testing.AdbDeviceTestHelper
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

fun AdbDeviceTestHelper.sourceUiHierarchy(): Observable<UiHierarchy> = with(adbDevice) {

    Observable.timer(5, TimeUnit.MILLISECONDS)
            .flatMap {
                if (AdbBusManager.uiHierarchyBusActive) {
                    AdbBusManager.uiHierarchyBus
                } else {
                    subscribeUiHierarchySource()
                }
            }
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.computation())
}