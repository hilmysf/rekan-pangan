package id.hilmysf.rekanpanganpi

import BottomBar
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import id.hilmysf.rekanpanganpi.navigation.AppNavHost
import id.hilmysf.rekanpanganpi.ui.theme.RekanPanganPITheme
import id.hilmysf.rekanpanganpi.viewmodel.AuthViewModel

@AndroidEntryPoint
class MainActivity
    : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RekanPanganPITheme {
                Surface(
                  modifier = Modifier.fillMaxSize()
                ) {
                    AppNavHost(

                    )
                }
            }
        }
    }
}
