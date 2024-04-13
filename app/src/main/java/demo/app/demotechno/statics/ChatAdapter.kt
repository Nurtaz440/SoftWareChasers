package demo.app.demotechno.statics

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import demo.app.demotechno.R
import demo.app.demotechno.databinding.RowChatBinding


class ChatAdapter(private val chatMessageList: List<ChatMessage>, private val context: Context) :
    RecyclerView.Adapter<ChatAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = RowChatBinding.inflate(inflater,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (chatMessageList[position].isMine()){
            holder.binding!!.leftSender.visibility = View.VISIBLE
            holder.binding!!.rightSender.visibility = View.GONE
            holder.binding!!.tvSender.setText(chatMessageList[position].text)
            holder.binding!!.tvSenderTime.setText(chatMessageList[position].convertTime())

            loadImageFromFirebase(holder.binding!!.profileImage,chatMessageList[position].senderId!!)
            holder.binding!!.profileImage.setOnClickListener {
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog)

                val profileImage = dialog.findViewById<View>(R.id.iv_profile) as ImageView
                loadImageFromFirebase(profileImage,chatMessageList[position].senderId!!)

                profileImage.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }else{
            holder.binding!!.rightSender.visibility = View.VISIBLE
            holder.binding!!.leftSender.visibility = View.GONE
            holder.binding!!.tvMe.setText(chatMessageList[position].text)
            holder.binding!!.tvMyTime.setText(chatMessageList[position].convertTime())
            loadImageFromFirebase(holder.binding!!.profileImageMine,chatMessageList[position].senderId!!)
            holder.binding!!.profileImageMine.setOnClickListener {
                val dialog = Dialog(context)
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                dialog.setCancelable(true)
                dialog.setContentView(R.layout.dialog)

                val profileImage = dialog.findViewById<View>(R.id.iv_profile) as ImageView
                loadImageFromFirebase(profileImage,chatMessageList[position].senderId!!)

                profileImage.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return chatMessageList.size
    }
    fun loadImageFromFirebase(imageView: ImageView,senderId : String) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("images/${senderId}")

        imagesRef.downloadUrl.addOnSuccessListener { uri ->
            // Load the image into ImageView using a library like Glide or Picasso
            Glide.with(context)
                .load(uri)
                .into(imageView)
        }.addOnFailureListener { exception ->
            // Handle failed download
        }
    }

    inner class MyViewHolder(binding: RowChatBinding) : RecyclerView.ViewHolder(binding.getRoot()) {
        //cache references to the individual views with in an item layout
        // of a recyclerview list
        var binding: RowChatBinding? = null

        init {
            this.binding = binding
        }
    }
}
