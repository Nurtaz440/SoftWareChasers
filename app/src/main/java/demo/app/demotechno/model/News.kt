package demo.app.demotechno.model

import com.google.firebase.Timestamp
import java.io.Serializable


class News: Serializable {
    var title: String? = null
    var imageUrl: String? = null
    var timestamp: Timestamp? = null
}
