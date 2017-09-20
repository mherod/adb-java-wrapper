package co.herod.adbwrapper.ext

import co.herod.adbwrapper.AdbCommand
import co.herod.adbwrapper.S
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.model.UiBounds
import co.herod.adbwrapper.model.UiNode
import co.herod.adbwrapper.observable
import co.herod.kotlin.ext.blocking
import java.util.concurrent.TimeUnit

fun AdbDevice.tap(uiNode: UiNode): String =
        tap(uiNode.bounds)

fun AdbDevice.tap(c: UiBounds): String =
        tap(c.centreX, c.centreY)

@JvmOverloads
fun AdbDevice.tap(centreX: Int = 0, centreY: Int = 0): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand("${S.SHELL} input tap $centreX $centreY")
                .observable()
                .blocking(2, TimeUnit.SECONDS)

@JvmOverloads
fun AdbDevice.swipe(x1: Int, y1: Int, x2: Int, y2: Int, speed: Int = 500): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand("${S.SHELL} input swipe $x1 $y1 $x2 $y2 $speed")
                .observable().lastOrError()
                .blocking(2, TimeUnit.SECONDS)

@JvmOverloads
fun AdbDevice.typeText(inputText: String = ""): String =
        AdbCommand.Builder()
                .setDevice(this)
                .setCommand("${S.SHELL} input text $inputText".trim())
                .observable().lastOrError()
                .blocking(2, TimeUnit.SECONDS)

