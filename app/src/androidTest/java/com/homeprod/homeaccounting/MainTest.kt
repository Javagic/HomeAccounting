package com.homeprod.homeaccounting

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import com.homeprod.homeaccounting.main.view.CreateAccountActivity
import com.homeprod.homeaccounting.main.view.CreateOperationActivity
import com.homeprod.homeaccounting.main.view.MainActivity
import org.hamcrest.CoreMatchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
open class MainTest {

    @get:Rule
    open val mainActivityTestRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @get:Rule
    open val operationTestRule =
        ActivityTestRule<CreateOperationActivity>(CreateOperationActivity::class.java, false, false)

    @get:Rule
    open val accountTestRule = ActivityTestRule<CreateAccountActivity>(CreateAccountActivity::class.java, false, false)


    fun addIncomeOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())
        onView(withId(R.id.etValue)).check(matches(isDisplayed()))

        onView(withId(R.id.rbOutgo)).perform(click())
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))

        onView(withId(R.id.rbIncome)).perform(click())

        onView(withId(R.id.etValue)).perform(typeText("2000"))
        onView(withId(R.id.etDescription)).perform(typeText("Description"))


        onView(withId(R.id.spTo)).perform(click())

        onView(withText(containsString("Account1"))).perform(click())
        onView(withId(R.id.spTo)).check(matches(withSpinnerText(containsString("Account1"))))
        onView(withId(R.id.btnSave)).perform(click())
    }

    fun addTransactionOperation() {
        onView(withId(R.id.fabCreateOperation)).perform(click())

        onView(withId(R.id.rbTransaction)).perform(click())
        onView(withId(R.id.spTo)).check(matches(isDisplayed()))
        onView(withId(R.id.spFrom)).check(matches(isDisplayed()))

        onView(withId(R.id.etValue)).perform(typeText("400"))
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
        mainActivityTestRule.launchActivity(null)
        createAccounts()
        addIncomeOperation()
        addOutgoOperation()
        addTransactionOperation()
        search()
        deleteOperation()
        deleteAccount()
    }

    fun search() {
        onView(withId(R.id.searchView)).perform(click())
        onView(withId(R.id.searchView)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("Account1"))
        onView(isAssignableFrom(EditText::class.java)).perform(replaceText("Description1"))
        onView(isAssignableFrom(EditText::class.java)).perform(replaceText("400"))
        onView(isAssignableFrom(EditText::class.java)).perform(replaceText(""), closeSoftKeyboard())
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


}
