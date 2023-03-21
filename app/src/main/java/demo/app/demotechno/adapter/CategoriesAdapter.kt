package demo.app.demotechno.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.databinding.ItemMainBinding
import demo.app.demotechno.model.CategoriesModel

class CategoriesAdapter(val context: Context,val clickListener : (Int)->Unit ) : RecyclerView.Adapter<CategoriesAdapter.VH>() {
    private var arrayList  = ArrayList<CategoriesModel>()

    fun setData(list:ArrayList<CategoriesModel>){
        this.arrayList = list
    }

    class VH(val context: Context,val binding:ItemMainBinding)
        :RecyclerView.ViewHolder(binding.root){

            fun onBind(item : CategoriesModel){
                binding.cvCategories.setCardBackgroundColor(ContextCompat.getColor(context,item.color!!))
                binding.tvTitle.text = item.title
                binding.ivIcon.setImageResource(item.image!!)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemMainBinding.inflate(inflater,parent,false)
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