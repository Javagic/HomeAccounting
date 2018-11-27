/*
 Created by Ilya Reznik
 reznikid@altarix.ru
 skype be3bapuahta
 on 28.11.18 18:19
 */

package com.homeprod.homeaccounting

import android.content.Intent
import android.os.Bundle
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import android.support.test.uiautomator.UiDevice
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.widget.EditText
import com.homeprod.homeaccounting.main.data.Account
import com.homeprod.homeaccounting.main.data.Operation
import com.homeprod.homeaccounting.main.repository.AccountRepository
import com.homeprod.homeaccounting.main.repository.OperationsRepository
import com.homeprod.homeaccounting.main.view.CreateAccountActivity
import com.homeprod.homeaccounting.main.view.CreateOperationActivity
import com.homeprod.homeaccounting.main.view.MainActivity
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
open class LifcycleTest {

    @get:Rule
    open val mainActivityTestRule = RestartActivityRule(MainActivity::class.java, false, false)

    @get:Rule
    open val operationTestRule =
        RestartActivityRule(CreateOperationActivity::class.java, false, false)

    @get:Rule
    open val accountTestRule = RestartActivityRule(CreateAccountActivity::class.java, false, false)

    @Before
    fun beforeTest() {
        val acc1 = Account("Account1", "AccountDescription1", 500)
        val acc2 = Account("Account2", "AccountDescription2", 500)
        AccountRepository.createAccount(acc1)
        AccountRepository.createAccount(acc2)
        OperationsRepository.createOperation(
            Operation(
                Operation.OperationType.TRANSACTION,
                200,
                acc1,
                acc2,
                "OperationDescription1"
            )
        )
        OperationsRepository.createOperation(
            Operation(
                Operation.OperationType.TRANSACTION,
                200,
                acc2,
                acc1,
                "OperationDescription2"
            )
        )
    }

    @Test
    fun testOperationLifecycle() {
        operationTestRule.launchActivity(null)
        Espresso.onView(ViewMatchers.withId(R.id.rbTransaction)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spTo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.spFrom)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.etValue)).perform(ViewActions.typeText("400"))
        Espresso.onView(ViewMatchers.withId(R.id.etDescription)).perform(ViewActions.typeText("Description"))

        Espresso.onView(ViewMatchers.withId(R.id.spFrom)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("Account1"))).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spFrom))
            .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("Account1"))))

        Espresso.onView(ViewMatchers.withId(R.id.spTo)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("Account2"))).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.spTo))
            .check(ViewAssertions.matches(ViewMatchers.withSpinnerText(CoreMatchers.containsString("Account2"))))
        testLifecycle(operationTestRule)
        Espresso.onView(ViewMatchers.withId(R.id.btnSave)).perform(ViewActions.click())
    }

    @Test
    fun testAccountLifecycle() {
        accountTestRule.launchActivity(null)
        onView(withId(R.id.etName)).perform(typeText("Account1"))
        onView(withId(R.id.etDescription)).perform(typeText("AccountDescription2"))
        testLifecycle(accountTestRule)
        onView(withId(R.id.btnSave)).perform(click())
    }

    @Test
    fun testMainLifecycle() {
        mainActivityTestRule.launchActivity(null)
        search()
        testLifecycle(mainActivityTestRule)
        deleteOperation()
        deleteAccount()
    }

    fun deleteAccount() {
        Espresso.onView(ViewMatchers.withId(R.id.rvAccounts)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.swipeLeft())
        )
        Espresso.onView(ViewMatchers.withText(CoreMatchers.containsString("Account2"))).perform(ViewActions.click())
    }

    fun deleteOperation() {
        Espresso.onView(ViewMatchers.withId(R.id.rvOperations)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(1, ViewActions.swipeLeft())
        )
    }

    fun search() {
        Espresso.onView(ViewMatchers.withId(R.id.searchView)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchView)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).perform(ViewActions.typeText("Account1"))
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java))
            .perform(ViewActions.replaceText("Description1"))
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java)).perform(ViewActions.replaceText("400"))
        Espresso.onView(ViewMatchers.isAssignableFrom(EditText::class.java))
            .perform(ViewActions.replaceText(""), ViewActions.closeSoftKeyboard())
    }


    fun waitMillis(millis: Long) {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + millis

        do {
        } while (System.currentTimeMillis() < endTime)
    }

    fun reopenActivity(rule: RestartActivityRule<*>) {

        val startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(rule.activity, startMain, Bundle())
        rule.relaunchActivity()
        waitMillis(1000)
    }

    fun testLifecycle(rule: RestartActivityRule<*>) {
        reopenActivity(rule)
        rotateActivity()
    }

    fun rotateActivity() {
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationLeft()
        UiDevice.getInstance(InstrumentationRegistry.getInstrumentation()).setOrientationNatural()
        waitMillis(1000)
    }
}
