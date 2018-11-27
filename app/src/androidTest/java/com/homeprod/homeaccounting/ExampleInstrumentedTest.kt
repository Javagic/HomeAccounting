package com.homeprod.homeaccounting

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.util.HumanReadables
import android.support.test.espresso.util.TreeIterables
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.View
import com.homeprod.homeaccounting.main.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeoutException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
open class ExampleInstrumentedTest {

    @get:Rule
    open val mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    fun addIncomeOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())
        onView(withId(R.id.etValue)).check(matches(isDisplayed()))

        onView(withId(R.id.rbOutgo)).perform(click())
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))

        onView(withId(R.id.rbIncome)).perform(click())

        onView(withId(R.id.etValue)).perform(typeText("2000"))
        onView(withId(R.id.etDescription)).perform(typeText("Description"))


        onView(withId(R.id.spTo)).perform(click())
//        onView(isRoot()).perform(waitId(R.id.etValue, 6000))

//        onData(allOf(withId(android.R.layout.simple_spinner_dropdown_item), withText("Account1(0$)"))).perform(click())
        onView(withText(containsString("Account1"))).perform(click())
        onView(withId(R.id.spTo)).check(matches(withSpinnerText(containsString("Account1"))))
        onView(withId(R.id.btnSave)).perform(click())
    }

    fun addTransactionOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())

        onView(withId(R.id.rbTransaction)).perform(click())
        onView(withId(R.id.spTo)).check(matches(isDisplayed()))
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))

        onView(withId(R.id.etValue)).perform(typeText("500"))
        onView(withId(R.id.etDescription)).perform(typeText("Description"))

        onView(withId(R.id.spFrom)).perform(click())
        onView(withText(containsString("Account1"))).perform(click())
        onView(withId(R.id.spFrom)).check(matches(withSpinnerText(containsString("Account1"))))

        onView(withId(R.id.spTo)).perform(click())
        onView(withText(containsString("Account2"))).perform(click())
        onView(withId(R.id.spTo)).check(matches(withSpinnerText(containsString("Account2"))))
        onView(withId(R.id.btnSave)).perform(click())
    }

    fun addOutgoOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())

        onView(withId(R.id.rbOutgo)).perform(click())
        onView(withId(R.id.etValue)).perform(typeText("1000"))
        onView(withId(R.id.etDescription)).perform(typeText("Description"))

        onView(withId(R.id.spFrom)).perform(click())
        onView(withText(containsString("Account1"))).perform(click())

        onView(withId(R.id.btnSave)).perform(click())
    }

    @Test
    fun useAppContext() {
        createAccounts()
        addIncomeOperation()
        addOutgoOperation()
        addTransactionOperation()
        deleteOperation()
        deleteAccount()
//        onView(isRoot()).perform(waitId(R.id.etValue, 16000))
    }

    fun deleteAccount() {
        onView(withId(R.id.rvAccounts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeLeft())
        )
        onView(withText(containsString("Account2"))).perform(click())
    }

    fun deleteOperation() {
        onView(withId(R.id.rvOperations)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, swipeLeft())
        )
    }

    fun addAccount(i: Int) {
        onView(withId(R.id.fabCreateAccount)).perform(click())
        onView(withId(R.id.etName)).perform(typeText("Account$i"))
        onView(withId(R.id.etDescription)).perform(typeText("AccountDescription$i"))
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.fabCreateAccount)).check(matches(isDisplayed()))
    }

    private fun createAccounts() {
        (1..2).forEach { addAccount(it) }
    }

    fun waitId(viewId: Int, millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()

            override fun getDescription(): String = "wait for a specific view with id <$viewId> during $millis millis."


            override fun perform(uiController: UiController, view: View) {
                uiController.loopMainThreadUntilIdle()
                val startTime = System.currentTimeMillis()
                val endTime = startTime + millis
                val viewMatcher = withId(viewId)

                do {
                    for (child in TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50)
                } while (System.currentTimeMillis() < endTime)

                // timeout happens
                throw PerformException.Builder()
                    .withActionDescription(this.description)
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(TimeoutException())
                    .build()
            }
        }
    }
}
