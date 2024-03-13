package demo.app.demotechno.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.databinding.NewsItemBinding
import demo.app.demotechno.model.News

class NewsAdapter(val context: Context, val arrayList: ArrayList<News>,val clickListener : (Int) ->Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: News) {
            binding.ivImage.setImageResource(item.image)
            binding.tvName.setText(item.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = NewsItemBinding.inflate(inflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount() = arrayList.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.itemView.setOnClickListener{
            clickListener.invoke(position)
        }
        holder.onBind(arrayList[position])
    }
}