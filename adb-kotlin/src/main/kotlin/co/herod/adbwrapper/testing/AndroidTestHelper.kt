package co.herod.adbwrapper.testing

import co.herod.adbwrapper.model.UiNode
import java.io.File
import java.util.concurrent.TimeUnit
import java.util.function.Predicate

/**
 * Created by matthewherod on 05/09/2017.
 */

@Suppress("RedundantVisibilityModifier")
public interface AndroidTestHelper {

    fun assertActivityName(activityName: String): String

    fun assertNotActivityName(activityName: String): String

    fun assertPower(minPower: Int)

    fun backButton()

    fun closeLeftDrawer()

    fun connectDevice()

    fun dismissDialog()

    fun dismissKeyboard()

    fun dragDown(widthFunction: Function1<Int, Int>, edgeOffset: Double = 0.2): String

    fun dragUp(widthFunction: Function1<Int, Int>, edgeOffset: Double = 0.2): String

    fun dragLeft(heightFunction: Function1<Int, Int>, edgeOffset: Double = 0.2): String

    fun dragRight(heightFunction: Function1<Int, Int>, edgeOffset: Double = 0.2): String

    fun failOnText(text: String)

    fun failOnText(text: String, timeout: Int = 30, timeUnit: TimeUnit = TimeUnit.SECONDS)

    fun getInstalledPackages(): List<String>

    fun getPackageVersionName(packageName: String): String?

    fun installApk(apkPath: String)

    fun installedPackageIsVersion(packageName: String, versionName: String): Boolean

    fun forceStopApp(packageName: String)

    fun launchApp(packageName: String)

    fun launchUrl(url: String)

    fun launchUrl(url: String, packageName: String)

    fun takeScreenshot(): File

    fun touchText(text: String): String

    fun typeText(text: String): String

    fun assertScreenOn()

    fun assertScreenOff()

    fun turnScreenOn()

    fun turnScreenOff()

    fun uninstallPackage(packageName: String)

    fun updateApk(apkPath: String)

    fun waitForTextToDisappear(text: String)

    fun waitForTextToFailToDisappear(text: String)

    fun waitForText(text: String, timeout: Int = 30, timeUnit: TimeUnit = TimeUnit.SECONDS): String

    fun waitForUiNode(uiNodePredicate: Predicate<UiNode>): String

    fun waitSeconds(waitSeconds: Int = 3)

    fun touchUiNode(uiNodePredicate: Predicate<UiNode>): String
}
