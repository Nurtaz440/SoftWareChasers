package demo.app.demotechno.regstrasion.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInViewModel: ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val signInResult: MutableLiveData<Boolean> = MutableLiveData()
    val errorMessage: MutableLiveData<String> = MutableLiveData()

    fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    signInResult.value = true
                } else {
                    signInResult.value = false
                    errorMessage.value = task.exception?.message ?: "Unknown error"
                }
            }
    }
}