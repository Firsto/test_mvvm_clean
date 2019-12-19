package studio.inprogress.yetanothertestapi

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.core.view.GravityCompat
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions.open
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.rule.ActivityTestRule
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test
import studio.inprogress.yetanothertestapi.ui.MainActivity

class NavigationTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun mainTest() {
        assertHomeScreen()

        openNavigation()

        assertNavigationOpened()

        Espresso.pressBack()

        assertHomeScreen()
    }

    private fun assertHomeScreen() {
        onView(allOf(withContentDescription(R.string.common_signin_button_text), isDisplayed()))
    }

    private fun openNavigation() {
        onView(ViewMatchers.withId(R.id.drawer_layout)).perform(open(GravityCompat.START))
    }

    private fun assertNavigationOpened() {
        onView(allOf(ViewMatchers.withId(R.id.drawer_layout), isDisplayed()))
    }

}