package co.herod.adbwrapper

import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.rx.FixedDurationTransformer
import co.herod.adbwrapper.rx.ResultChangeFixedDurationTransformer
import co.herod.adbwrapper.util.UiHelper
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

@Suppress("MemberVisibilityCanPrivate")
object AdbStreams {

    private fun adbBus() = AdbBusManager.throttledBus()

    fun streamUiHierarchyUpdates() = adbBus()?.filter { it.isUiDumpOutput() }

    fun streamAdbCommands() = adbBus()?.filter { it.isAdbInput() }

    fun streamUiNodeChanges(): Observable<UiNode>? = streamUiHierarchyUpdates()?.map { it.parseNode() }?.compose(ResultChangeFixedDurationTransformer())?.compose { UiHelper.uiXmlToNodes(it) }?.compose(FixedDurationTransformer(1, TimeUnit.DAYS))

    private fun String.isAdbInput(): Boolean = startsWith("adb ") && trim() != "Killed"

    private fun String.isUiDumpOutput(): Boolean = contains("<node") && contains("bounds=")

    private fun String.parseNode(): String = substring(indexOf('<'), lastIndexOf('>') + 1)

}