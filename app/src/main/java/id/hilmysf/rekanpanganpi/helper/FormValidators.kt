package id.hilmysf.rekanpanganpi.helper

object FormValidators {

    fun validateFullName(fullName: String): String? {
        return if (fullName.isBlank()) {
            "Nama lengkap tidak boleh kosong"
        } else {
            null // No error
        }
    }

    fun validateEmail(email: String): String? {
        print("android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches():${android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()}")
        return if (email.isBlank()) {
            "Email tidak boleh kosong"
        }
//        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            "Format email tidak valid"
//        }
        else {
            null
        }
    }

    fun validatePassword(password: String): String? {
        val minLength = 8
        return if (password.isBlank()) {
            "Password tidak boleh kosong"
        } else if (password.length < minLength) {
            "Password minimal $minLength karakter"
        } else {
            null
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): String? {
        return if (confirmPassword.isBlank()) {
            "Konfirmasi password tidak boleh kosong"
        } else if (password != confirmPassword) {
            "Password tidak cocok"
        } else {
            null
        }
    }
}