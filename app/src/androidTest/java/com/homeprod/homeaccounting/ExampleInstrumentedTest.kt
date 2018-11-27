package com.homeprod.homeaccounting

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.PerformException
import android.support.test.espresso.UiController
import android.support.test.espresso.ViewAction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.espresso.util.HumanReadables
import android.support.test.espresso.util.TreeIterables
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.view.View
import com.homeprod.homeaccounting.main.MainActivity
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
class ExampleInstrumentedTest {
    @get:Rule
    public open val mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun addOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())
        onView(withId(R.id.etValue)).check(matches(isDisplayed()))

        onView(withId(R.id.rbOutgo)).perform(click())
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))

        onView(withId(R.id.rbTransaction)).perform(click())
        onView(withId(R.id.spTo)).check(matches(isDisplayed()))
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))


        onView(withId(R.id.etValue)).perform(typeText("2000"))
        onView(withId(R.id.etDescription)).perform(typeText("Description1"))

        onView(withId(R.id.btnSave)).perform(click())
        waitId(R.id.fabCreateAccount, 50000)

//        onView(withId(R.id.spFrom)).perform(click())
//        onData(allOf(`is`(instanceOf(String::class.java)), `is`(selectionText))).perform(click())
//        onView(withId(R.id.spFrom)).check(matches(withSpinnerText(containsString(selectionText))));
    }

    @Test
    fun useAppContext() {
        createAccounts()
        addOperation()
        onView(isRoot()).perform(waitId(R.id.etValue, 5000))
    }

    fun addAccount(i: Int) {
        onView(withId(R.id.fabCreateAccount)).perform(click())
        onView(withId(R.id.etName)).perform(typeText("Account$i"))
        onView(withId(R.id.etDescription)).perform(typeText("AccountDescription$i"))
        onView(withId(R.id.btnSave)).perform(click())
        onView(withId(R.id.fabCreateAccount)).check(matches(isDisplayed()))
    }

    private fun createAccounts() {
        (1..5).forEach { addAccount(it) }
    }

    private fun createAutoOperations() {
//        (1..10).forEach {
//            OperationsRepository.createOperation(
//                Operation(Type)
//            )
//        }
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
