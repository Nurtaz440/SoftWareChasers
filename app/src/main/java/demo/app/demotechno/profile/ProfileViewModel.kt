package demo.app.demotechno.profile

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import demo.app.demotechno.model.UserInfo

class ProfileViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val userInfoMutableLiveData: MutableLiveData<UserInfo> = MutableLiveData()
    val userInfo: LiveData<UserInfo> = userInfoMutableLiveData

    init {
        // Fetch user information from Realtime Database
        val user = auth.currentUser
        val userId = user?.uid
        userId?.let {
            val userRef = database.getReference("users").child(it)
            userRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val number = snapshot.child("number").getValue(String::class.java)
                    val userInfo = UserInfo(name, email, number)
                    userInfoMutableLiveData.value = userInfo
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error

                }
            })
        }
    }
    fun signOut() {
        auth.signOut()
    }
}