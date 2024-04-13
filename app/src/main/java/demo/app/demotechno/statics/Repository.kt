package demo.app.demotechno.statics

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Repository {
    //it acts as abridge between the ViewModel and the data Source
    var database: FirebaseDatabase
    var reference: DatabaseReference
    var groupReference: DatabaseReference? = null
    var messageLiveData: MutableLiveData<List<ChatMessage?>>

    init {
        database = FirebaseDatabase.getInstance()
        reference = database.reference
        messageLiveData = MutableLiveData()
    }



    //getting message live data
    fun getMessageLiveData(groupName: String?): MutableLiveData<List<ChatMessage?>> {
        groupReference = database.reference.child(groupName!!)
        val messageList: MutableList<ChatMessage?> = ArrayList()
        groupReference!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()
                for (dataSnapshot in snapshot.children) {
                    val message = dataSnapshot.getValue(ChatMessage::class.java)
                    messageList.add(message)
                }
                messageLiveData.postValue(messageList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
        return messageLiveData
    }

    fun senMessage(messageText: String, chatGroup: String?) {
        val ref = database.getReference(chatGroup!!)
        if (messageText.trim { it <= ' ' } != "") {
            val msg = ChatMessage(
                FirebaseAuth.getInstance().currentUser!!.uid,
                messageText,
                System.currentTimeMillis()
            )
            val randomKey = ref.push().key
            ref.child(randomKey!!).setValue(msg)
        }
    }
}
