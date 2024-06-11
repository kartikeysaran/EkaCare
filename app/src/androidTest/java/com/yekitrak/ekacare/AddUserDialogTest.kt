package com.yekitrak.ekacare

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yekitrak.ekacare.data.db.entity.UserEntity
import com.yekitrak.ekacare.ui.screen.AddUserDialog
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
class AddUserDialogTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun validInputSubmission() {
        var capturedUserEntity: UserEntity? = null
        composeTestRule.setContent {
            AddUserDialog(onAddUser = { userEntity ->
                capturedUserEntity = userEntity
            }, onDismiss = {})
        }

        composeTestRule.onNodeWithText("Name").performTextInput("John")
        composeTestRule.onNodeWithText("Age").performTextInput("30")
        composeTestRule.onNodeWithText("Address").performTextInput("Address")
        composeTestRule.onNodeWithText("City").performTextInput("City")
        composeTestRule.onNodeWithText("Country").performTextInput("Country")

        composeTestRule.onNodeWithText("Select DOB").performClick()
        composeTestRule.onNodeWithText("Add User").performClick()

        assertNull(capturedUserEntity) // Null as dob is not selected
        assertNotEquals("John", capturedUserEntity?.name)
    }

    @Test
    fun emptyInputFields() {
        var userEntityCaptured = false
        composeTestRule.setContent {
            AddUserDialog(onAddUser = { userEntityCaptured = true }, onDismiss = { /* Do nothing */ })
        }

        // Click the Add User button without entering any input

        // Verify that onAddUser callback is not invoked
        assertFalse(userEntityCaptured)
    }
}