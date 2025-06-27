package com.example.calculator

import androidx.fragment.app.testing.FragmentScenario
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun inputAndCalculationResultTest() {
        val scenario = FragmentScenario.launchInContainer(
            CalculatorFragment::class.java, null
        )
        onView(withId(R.id.editTextText2)).perform(typeText("3+5-8*7+0"))
            .check(matches(withText("3+5-8*7+0")))
        onView(withId(R.id.buttonEquals)).perform(click())
        onView(withId(R.id.textAnswer)).check(matches(withText("-48.0")))
        scenario.close()
    }

    @Test
    fun navigationTestFromCalculatorFragmentToUserProfileAndBack() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.buttonProfile)).perform(click())
        onView(withId(R.id.buttonBackUserProfile)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonBackUserProfile)).perform(click())
        onView(withId(R.id.buttonProfile)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun navigationTestFromCalculatorFragmentToLogPageAndBack() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.buttonLog)).perform(click())
        onView(withId(R.id.buttonBackLogPage)).check(matches(isDisplayed()))
        onView(withId(R.id.buttonBackLogPage)).perform(click())
        onView(withId(R.id.buttonLog)).check(matches(isDisplayed()))
        scenario.close()
    }

    @Test
    fun testRecyclerViewItem() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.editTextText2)).perform(typeText("4+5-2*3+1"))
        onView(withId(R.id.buttonEquals)).perform(click())
        onView(withId(R.id.editTextText2)).perform(typeText("3+5-8*7+0"))
        onView(withId(R.id.buttonEquals)).perform(click())
        onView(withId(R.id.buttonLog)).perform(click())
        onView(withId(R.id.rvLog))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(1))
            .check(matches(hasDescendant(withText("4+5-2*3+1 = 4.0"))))
        scenario.close()
    }
}