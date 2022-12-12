package com.example.surveyapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.surveyapp.model.Admin
import com.example.surveyapp.model.DataBaseHelper

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.surveyapp", appContext.packageName)
    }


    @Test
    fun createUserAccount() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val database = DataBaseHelper(appContext)
        // Creating a username with random value up to the Integer Max Value
        val username = (0..Int.MAX_VALUE).random().toString()

        val adminAccount = Admin(-1, username, "password")
        assertTrue("Testing if $adminAccount is added to database, should return greater than 0 (ID)",
            database.addAdmin(adminAccount) > 0)
    }
}




















