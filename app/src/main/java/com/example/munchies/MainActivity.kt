package com.example.munchies
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.munchies.navigation.AppNavigation
import com.example.munchies.ui.theme.MunchiesTheme
import com.example.munchies.viewmodel.HomeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Instantiate the HomeViewModel
        val homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setContent {
            MunchiesTheme {
                val navController = rememberNavController() // Create NavController
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        AppNavigation(
                            homeViewModel = homeViewModel, // Pass the ViewModel to AppNavigation
                            modifier = Modifier.padding(innerPadding) // Apply padding to AppNavigation


                        )
                    }
                )
            }
        }
    }
}

