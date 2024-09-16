package com.example.composemoviebox

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavHostController
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BottomNavTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        // TestNavHostController ile bir NavController oluşturuyoruz
        composeTestRule.activity.apply {
            navController = TestNavHostController(this)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            // Test etmek için MoviesScreen bileşenini yüklüyoruz
            // Burada ayrıca `setContent`'e gerek yok, `MainActivity` zaten içerik yüklüyor
        }
    }

    @Test
    fun testBottomNavChangesScreen() {
        // Başlangıçta Home ekranında olduğumuzu doğrula
        composeTestRule.onNodeWithText("Movies").assertExists()

        // Favorites butonuna tıkla
        composeTestRule.onNodeWithText("Favorites").performClick()

        // Favoriler ekranının yüklendiğini doğrula
        composeTestRule.onNodeWithText("Favorites").assertExists()

        // Settings butonuna tıkla
        composeTestRule.onNodeWithText("Chat").performClick()

//        // Ayarlar ekranının yüklendiğini doğrula
//        composeTestRule.onNodeWithText("Chat").assertExists()

//        // Tekrar Home butonuna tıkla ve Home ekranına geri dön
//        composeTestRule.onNodeWithText("Movies").performClick()
//
//        // Home ekranının yüklendiğini doğrula
//        composeTestRule.onNodeWithText("Movies").assertDoesNotExist()

        // BottomNavigationBar'ın görünmediğini doğrula
        composeTestRule.onNodeWithContentDescription("Movies").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Favorites").assertDoesNotExist()
        composeTestRule.onNodeWithContentDescription("Chat").assertDoesNotExist()
    }
}
