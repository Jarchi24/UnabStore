package me.jarvierlunadiaz.unabstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import me.jarvierlunadiaz.unabstore.ui.theme.UnabStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            var startDestination = "Login"

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            if(currentUser != null){
                startDestination  = "home"
            }else{
                startDestination = "Login"
            }

            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = "Login") {
                    LoginScreen(onClickRegister = {
                        navController.navigate("Register")
                    }, onSuccessfulLogin = {
                        navController.navigate("Home"){
                            popUpTo("Login"){
                                inclusive = true
                            }
                        }
                    })
                }
                composable(route = "Register") {
                    RegisterScreen(onClickBack = {
                        navController.popBackStack()
                    },onSuccesfullRegister = {
                        navController.navigate("Login"){
                            popUpTo("Register"){
                                inclusive = true
                            }
                        }
                    })
                }
                composable(route = "Home") {
                    HomeScreen(onClickLogout = {
                        navController.navigate("Login"){
                            popUpTo(0)
                        }
                    })
                }
            }
        }
    }
}


