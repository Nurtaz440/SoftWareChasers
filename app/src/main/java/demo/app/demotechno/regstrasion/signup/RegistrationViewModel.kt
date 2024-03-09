package demo.app.demotechno.regstrasion.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    val registrationResult: MutableLiveData<Boolean> = MutableLiveData()

    fun registerUser(email: String, password: String,number:String,name:String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Save user information to Realtime Database
                    val user = auth.currentUser
                    val userId = user?.uid
                    userId?.let {
                        val userRef = database.getReference("users").child(it)
                        val userInfo = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "number" to number
                        )
                        userRef.setValue(userInfo)
                    }
                    registrationResult.value = true
                } else {
                    registrationResult.value = false
                }
            }
    }
}