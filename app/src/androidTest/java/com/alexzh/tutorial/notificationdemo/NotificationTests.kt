package com.alexzh.tutorial.notificationdemo

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.*
import android.view.View
import android.widget.Button
import com.alexzh.tutorial.notificationdemo.adapter.CityViewHolder
import com.alexzh.tutorial.notificationdemo.data.DummyData
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * The {@link hideNotification() hideNotification} method commented
 * in test cases because I disabled floating notification use ADB using the
 * <pre>
 * {@code adb shell settings put global heads_up_notifications_enabled 0}
 * </pre>
 */
@RunWith(AndroidJUnit4::class)
class NotificationTests {
    private val amsterdamId = 1L
    private val timeout = 3_000L
    private val firstItemPos = 0

    private val expectedAllCitiesActionRes = "android:id/action0"
    private val expectedText = DummyData.getCityById(amsterdamId).description

    private val notificationHeaderRes = "android:id/notification_header"
    private val clearAllNotificationRes = "com.android.systemui:id/dismiss_text"
    private val allAppsLauncherRes = "com.google.android.apps.nexuslauncher:id/drag_indicator"

    private val expectedAppName by lazy { activityRule.activity.getString(R.string.app_name) }
    private val expectedAllCities by lazy { activityRule.activity.getString(R.string.notification_action_all_cities) }
    private val expectedTitle by lazy { activityRule.activity.getString(R.string.notification_title) }
    private val expectedSource by lazy { activityRule.activity.getString(R.string.source) }

    private val uiDevice by lazy {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
    }

    @Rule @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun shouldOpenDetailsInformationAboutAmsterdam() {
        onView(withId(R.id.list))
                .perform(actionOnItemAtPosition<CityViewHolder>(firstItemPos, click()))

        onView(withId(R.id.source_textView))
                .check(matches(withText(expectedSource)))
    }

    @Test
    fun shouldSendNotificationWhichContainsTitleTextAndAllCities() {
        onView(withId(R.id.list))
                .perform(actionOnItemAtPosition<CityViewHolder>(firstItemPos, clickOnSendNotification()))

        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        val title: UiObject2 = uiDevice.findObject(By.text(expectedTitle))
        val text: UiObject2 = uiDevice.findObject(By.textStartsWith(expectedText))
        val allCities: UiObject2 = uiDevice.findObject(By.res(expectedAllCitiesActionRes))
        assertEquals(expectedTitle, title.text)
        assertTrue(text.text.startsWith(expectedText))
        assertEquals(expectedAllCities.toLowerCase(), allCities.text.toLowerCase())

        clearAllNotifications()
    }

    @Test
    fun shouldClickOnNotificationOpenDetailsScreen() {
        onView(withId(R.id.list))
                .perform(actionOnItemAtPosition<CityViewHolder>(firstItemPos, clickOnSendNotification()))

        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        val text: UiObject2 = uiDevice.findObject(By.textStartsWith(expectedText))
        text.click()
        uiDevice.wait(Until.hasObject(By.text(expectedText)), timeout)

        onView(withId(R.id.description_textView))
                .check(matches(withText(expectedText)))

        onView(withId(R.id.source_textView))
                .check(matches(withText(expectedSource)))
    }

    @Test
    fun shouldClickOnAllCitiesOpenAllCitiesList() {
        val allNotificationAsBundleText: String =
                activityRule.activity.getText(R.string.title_send_all_notifications).toString()

        onView(withId(R.id.list))
                .perform(actionOnItemAtPosition<CityViewHolder>(firstItemPos, clickOnSendNotification()))

        uiDevice.pressBack()

        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        val allCities: UiObject2 = uiDevice.findObject(By.res(expectedAllCitiesActionRes))
        allCities.click()
        uiDevice.wait(Until.hasObject(By.text(allNotificationAsBundleText)), timeout)

        onView(withId(R.id.send_all_notifications))
                .check(matches(withText(R.string.title_send_all_notifications)))
    }

    @Test
    fun shouldClickOnFirstNotificationOfNotificationBundle() {
        onView(withId(R.id.send_all_notifications))
                .perform(click())

        /*hideNotification()*/
        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        val notificationHeader: UiObject2 = uiDevice.findObject(By.res(notificationHeaderRes))
        notificationHeader.swipe(Direction.DOWN, 0.05f)

        val amsterdamNotification: UiObject2 = uiDevice.findObject(By.text(expectedText))
        amsterdamNotification.click()
        uiDevice.wait(Until.hasObject(By.text(expectedText)), timeout)

        onView(withId(R.id.description_textView))
                .check(matches(withText(expectedText)))

        clearAllNotifications()
    }

    @Test
    fun shouldClickOnAppIconAndVerifyNotificationBadge() {
        onView(withId(R.id.list))
                .perform(actionOnItemAtPosition<CityViewHolder>(firstItemPos, clickOnSendNotification()))

        uiDevice.pressHome()
        /*hideNotification()*/
        val allApps : UiObject2 = uiDevice.findObject(By.res(allAppsLauncherRes))
        allApps.click()

        uiDevice.wait(Until.hasObject(By.text(expectedAppName)), timeout)
        val app: UiObject2 = uiDevice.findObject(By.text(expectedAppName))
        app.longClick()

        val amsterdamNotification: UiObject2 = uiDevice.findObject(By.text(expectedText))
        amsterdamNotification.click()
        uiDevice.wait(Until.hasObject(By.text(expectedText)), timeout)
        onView(withId(R.id.description_textView))
                .check(matches(withText(expectedText)))
    }

    @After
    fun tearDown() {
        uiDevice.pressBack()
    }

    private fun clickOnSendNotification() : ViewAction {
        return object : ViewAction {
            override fun getDescription(): String {
                return "Click on the send notification button"
            }

            override fun getConstraints(): Matcher<View> {
                return Matchers.allOf(isDisplayed(), isAssignableFrom(Button::class.java))
            }

            override fun perform(uiController: UiController?, view: View?) {
                view?.findViewById<View>(R.id.send_button)?.performClick()
            }
        }
    }

    /**
     * Workaround for swiping down the floating notification.
     * As an alternative, we can turn off them using the
     * <pre>
     * {@code adb shell settings put global heads_up_notifications_enabled 0}
     * </pre>
     */
    @SuppressWarnings("unused")
    private fun hideNotification() {
        uiDevice.swipe(
                200,
                200,
                200,
                100,
                5
        )
    }

    private fun clearAllNotifications() {
        uiDevice.openNotification()
        uiDevice.wait(Until.hasObject(By.textStartsWith(expectedAppName)), timeout)
        val clearAll: UiObject2 = uiDevice.findObject(By.res(clearAllNotificationRes))
        clearAll.click()
    }
}