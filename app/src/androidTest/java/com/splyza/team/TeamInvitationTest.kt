package com.splyza.team

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TeamInvitationTest {
    @get:Rule
    val activityTestRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.splyza.team", appContext.packageName)
    }

    @Test
    fun startInviteTeamThenShowQrCode() {
        onView(withId(R.id.invite_button)).perform(click())
        onView(withId(R.id.permission_spinner)).check(matches(isDisplayed()))
        // Check the test values of member/supporter count and limit.
        onView(withId(R.id.member_count)).check(matches(withSubstring("83")))
        onView(withId(R.id.member_limit)).check(matches(withSubstring("100")))
        // Transit to QrCodeActivity with generated QR image.
        onView(withId(R.id.share_qr_code_btn)).perform(click())
        onView(withId(R.id.qrcode_image)).check(matches(isDisplayed()))
    }
}