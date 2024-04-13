package demo.app.demotechno.statics

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


class MyViewModel(application: Application) : AndroidViewModel(application) {
    var repository: Repository

    init {
        repository = Repository()
    }



    //messages
    fun getMessagesLiveData(groupName: String?): MutableLiveData<List<ChatMessage?>> {
        return repository.getMessageLiveData(groupName)
    }

    fun sendMessage(msg: String?, chatGroup: String?) {
        repository.senMessage(msg!!, chatGroup)
    }
}

