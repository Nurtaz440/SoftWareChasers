package demo.app.demotechno.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.databinding.ItemEBooksBinding
import demo.app.demotechno.model.EbooksModel

class EBoosAdapter (val context: Context, val clickListener : (Int)->Unit ) : RecyclerView.Adapter<EBoosAdapter.VH>() {
    private var arrayList  = ArrayList<EbooksModel>()

    fun setData(list:ArrayList<EbooksModel>){
        this.arrayList = list
    }

    class VH(val context: Context, val binding: ItemEBooksBinding)
        : RecyclerView.ViewHolder(binding.root){

        fun onBind(item : EbooksModel){
            binding.cvEBooks.setCardBackgroundColor(ContextCompat.getColor(context,item.color!!))
            binding.tvGrade.text = item.title
            binding.tvGrade.setTextColor(ContextCompat.getColor(context,item.colorText!!))
            binding.ivImage.setImageResource(item.image!!)
            binding.ivNumber.setImageResource(item.number!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemEBooksBinding.inflate(inflater,parent,false)
        return VH(context = context, view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(arrayList[position])
        holder.itemView.setOnClickListener {
            clickListener.invoke(position)
        }
    }

    override fun getItemCount()=arrayList.size
}