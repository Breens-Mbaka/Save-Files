package com.breens.savefiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.breens.savefiles.ui.theme.SaveFilesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val saveFilesViewModel: SaveFilesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val absolutePath = saveFilesViewModel.imageAbsolutePath.value

            SaveFilesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = "home") {
                        composable(route = "home") {
                            HomeScreen(
                                onNavigateToSavedImages = { imageAbsolutePath ->
                                    saveFilesViewModel.setImageAbsolutePath(imageAbsolutePath = imageAbsolutePath)
                                    navController.navigate("saved_images")
                                }
                            )
                        }
                        composable(route = "saved_images") {
                            SavedImagesScreen(
                                absolutePath = absolutePath,
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}