package co.herod.adbwrapper.testing.device

import co.herod.adbwrapper.AdbDeviceManager
import co.herod.adbwrapper.AdbPackageManager
import co.herod.adbwrapper.model.AdbDevice
import co.herod.adbwrapper.testing.forceStopApp
import co.herod.adbwrapper.testing.getActivityName
import co.herod.adbwrapper.testing.testHelper
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetActivityNameKtTest {

    private val settingsPackageName = "com.android.settings"

    private var adbDevice: AdbDevice? = null

    @Before
    fun setUp() {
        adbDevice = AdbDeviceManager.getDevice()
    }

    @After
    fun tearDown() {
        adbDevice?.testHelper()?.forceStopApp(settingsPackageName)
    }

    @Test
    fun getActivityNameWhenSettings() {
        adbDevice?.run {
            AdbPackageManager.launchApp(this, settingsPackageName)
            testHelper().getActivityName().run {
                Assert.assertEquals(this, "Settings")
            }
        }
    }

    @Test
    fun getActivityNameWhenSettingsFullQualified() {
        adbDevice?.run {
            AdbPackageManager.launchApp(this, settingsPackageName)
            testHelper().getActivityName(true).run {
                Assert.assertEquals(this, "$settingsPackageName/.Settings")
            }
        }
    }
}