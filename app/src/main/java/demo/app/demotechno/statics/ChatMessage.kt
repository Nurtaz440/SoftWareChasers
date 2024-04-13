package demo.app.demotechno.statics

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone


class ChatMessage {
    var senderId: String? = null
    var text: String? = null
    var time: Long = 0


    constructor(senderId: String?, text: String?, time: Long) {
        this.senderId = senderId
        this.text = text
        this.time = time
    }

 constructor()

    fun isMine(): Boolean {
         if (senderId == FirebaseAuth.getInstance().currentUser!!.uid) {
             return false
         }
        return  true
    }


    fun convertTime(): String {
        val sdf = SimpleDateFormat("HH:mm")
        val date = Date(time)
        sdf.timeZone = TimeZone.getDefault()
        return sdf.format(date)
    }
}

