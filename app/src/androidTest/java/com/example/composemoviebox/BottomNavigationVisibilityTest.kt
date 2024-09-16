package com.example.composemoviebox

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.example.composemoviebox.ui.movie.MoviesScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class BottomNavigationVisibilityTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)


    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun init() {
        hiltRule.inject()  // Hilt injection for the test
    }

    @Test
    fun bottomNavigation_isNotDisplayed_onDetailsScreen() {
        // Set up the Composable content
        composeTestRule.setContent {
            val navController = rememberNavController()
            MoviesScreen(navController = navController)
        }

        // Navigate to the details screen
        composeTestRule.onNodeWithText("Inside Out 2").performClick()

        // Assert that the BottomNavigation is not displayed on the details screen
        composeTestRule.onNodeWithTag("BottomNavigation")
            .assertDoesNotExist()  // Ensure BottomNavigation doesn't exist on the Details screen
    }
}