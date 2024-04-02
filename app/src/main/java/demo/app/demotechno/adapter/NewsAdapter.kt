package demo.app.demotechno.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import demo.app.demotechno.R
import demo.app.demotechno.model.News


class NewsAdapter(private val context: Context,private val journalList: ArrayList<News>,
                  private val listener : (Int) -> Unit) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context)
            .inflate(R.layout.news_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentJournal: News = journalList[position]
        holder.thoughts.setText(currentJournal.title)
        val imageUrl: String = currentJournal.imageUrl!!
        val timeAgo = DateUtils
            .getRelativeTimeSpanString(
                currentJournal.timestamp!!.getSeconds() * 1000
            ) as String
        holder.dateAdded.text = timeAgo

        holder.itemView.setOnClickListener {
            listener.invoke(position)
        }

        //Glide
        Glide.with(context)
            .load(imageUrl)
            .fitCenter()
            .into(holder.images)
    }

    override fun getItemCount(): Int {
        return journalList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thoughts: TextView
        var dateAdded: TextView
        var images: ImageView
        var shareButton: ImageView

        init {
            dateAdded = itemView.findViewById<TextView>(R.id.journal_date)
            thoughts = itemView.findViewById<TextView>(R.id.journal_desc)
            images = itemView.findViewById<ImageView>(R.id.journal_image_list)
            shareButton = itemView.findViewById<ImageView>(R.id.journal_row_share_button)
            shareButton.setOnClickListener { }
        }
    }
}
