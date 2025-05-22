import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import id.hilmysf.rekanpanganpi.helper.FormValidators
import id.hilmysf.rekanpanganpi.ui.components.AppTextField
import id.hilmysf.rekanpanganpi.viewmodel.AuthViewModel

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onRegisterSuccess: () -> Unit,
) {
    var fullname by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }

    val isLoading = viewModel.isLoading
    val registerState = viewModel.registerState

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Register",
            fontSize = 40.sp,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppTextField(
            value = fullname,
            onValueChange = {
                fullname = it
                fullNameError = null
            },
            label = "Full Name",
            enabled = !isLoading,
            errorMessage = fullNameError,
            modifier = Modifier.fillMaxWidth()
        )
        AppTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = null
            },
            label = "Email",
            enabled = !isLoading,
            keyboardType = KeyboardType.Email,
            errorMessage = emailError,
            modifier = Modifier.fillMaxWidth(),
        )
        AppTextField(
            isPassword = true,

            value = password,
            onValueChange = { password = it },
            label = "Password",
            errorMessage = passwordError,
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        )
        AppTextField(
            isPassword = true,
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = "Confirm Password",
            enabled = !isLoading,
            errorMessage = confirmPasswordError,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                fullNameError = FormValidators.validateFullName(fullName = fullname)
                emailError = FormValidators.validateEmail(email = email)
                passwordError = FormValidators.validatePassword(password = password)
                confirmPasswordError = FormValidators.validateConfirmPassword(
                    password = password,
                    confirmPassword = confirmPassword
                )

                val isFormValid =
                    fullNameError == null && emailError == null && passwordError == null && confirmPasswordError == null
                if (isFormValid) {
                    viewModel.register(email, password, fullname)
                    onRegisterSuccess()
                }


            },
            enabled = !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    color = White,
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Registering...")
            } else {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        if (registerState == null) {
            Dialog(
                onDismissRequest = {},
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(White, shape = RoundedCornerShape(8.dp))
                ) {
                    CircularProgressIndicator()
                }
            }
        } else if (registerState.isSuccess) {
            if (registerState.getOrNull() == true) {
                onRegisterSuccess()
            }
        } else {
            registerState.exceptionOrNull()?.message?.let {
                Text("Error: $it", color = Color.Red)
            }
        }
    }
}
