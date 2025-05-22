package id.hilmysf.rekanpanganpi.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import id.hilmysf.rekanpanganpi.data.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var loginState by mutableStateOf<Result<Boolean>?>(Result.success(false))
        private set

    var registerState by mutableStateOf<Result<Boolean>?>(Result.success(false))
        private set

    fun login(email: String, password: String) {
        isLoading = true
        loginState = null

        authRepository.login(email, password) { success, err ->
            isLoading = false
            loginState = if (success) {
                Result.success(true)
            } else {
                Result.failure(Exception(err))
            }
        }
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    fun register(email: String, password: String, username: String) {
        isLoading = true
        registerState = null

        authRepository.register(email, password, username) { success, err ->
            isLoading = false
            registerState = if (success) {
                Result.success(true)
            } else {
                Result.failure(Exception(err))
            }
        }

    }
}