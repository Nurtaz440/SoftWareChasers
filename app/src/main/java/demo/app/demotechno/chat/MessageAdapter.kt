package demo.app.demotechno.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.databinding.ItemChatBinding

class MessageAdapter(private val messageList : ArrayList<Message>) : RecyclerView.Adapter<MessageAdapter.VH>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

        return VH(ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val message = messageList[position]
        if (message.sentBy.equals(SENT_BY_NAME)){
            holder.leftChatView.visibility = View.GONE
            holder.rightChatView.visibility = View.VISIBLE
            holder.rightTextView.text = message.message
        }else{
            holder.rightChatView.visibility = View.GONE
            holder.leftChatView.visibility = View.VISIBLE
            holder.leftTextView.text = message.message
        }

    }

    override fun getItemCount() = messageList.size

    class VH(val binding: ItemChatBinding) : RecyclerView.ViewHolder(binding.root){
        val leftChatView = binding.leftChatView
        val leftTextView = binding.leftChatTextView
        val rightChatView = binding.rightChatView
        val rightTextView = binding.rightChatTextView
    }

}