package demo.app.demotechno.chat

const val SENT_BY_NAME = "me"
const val SENT_BY_BOT = "bot"

 data class Message(
     val message : String,
     val sentBy : String
 )
