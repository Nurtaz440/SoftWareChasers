package demo.app.demotechno.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.R
import demo.app.demotechno.databinding.SlideItemLayoutBinding
import demo.app.demotechno.model.IntroSlide

class IntroSliderAdapter(val context: Context)
    : RecyclerView.Adapter<IntroSliderAdapter.IntroSlideViewHolder>(){
    //for adding text to speech listener in the onboarding fragment
    //var onTextPassed: ((textView: TextView) -> Unit)? = null

     private val introSlides = ArrayList<IntroSlide>()

    fun setData(itemList: List<IntroSlide>) {
        introSlides.clear()
        introSlides.addAll(itemList)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntroSlideViewHolder {
        return IntroSlideViewHolder(context,
            SlideItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return introSlides.size
    }

    override fun onBindViewHolder(holder: IntroSlideViewHolder, position: Int) {
        if (position % 2 == 0){
            holder.cvMain.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.cv_back_first))
            holder.ivCircule.visibility = View.VISIBLE
            holder.ivCircule2.visibility = View.VISIBLE
            holder.ivCircule3.visibility = View.GONE
            holder.ivCircule4.visibility = View.GONE
        }else{
            holder.cvMain.setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.cv_back_second))
            holder.ivCircule.visibility = View.GONE
            holder.ivCircule2.visibility = View.GONE
            holder.ivCircule3.visibility = View.VISIBLE
            holder.ivCircule4.visibility = View.VISIBLE
        }
        when(position){
            1->{
                holder.ivImage.setPadding(20,20,20,20)
                holder.ivIcon.visibility = View.VISIBLE
                holder.ivIcon2.visibility = View.VISIBLE
            }
        }
        holder.bind(introSlides[position])
    }

     class IntroSlideViewHolder( val mcontext :Context, private val binding: SlideItemLayoutBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val cvMain = binding.cvMain
        val ivImage = binding.imageSlideIcon
        val ivIcon = binding.iv
        val ivIcon2 = binding.iv2
        val ivCircule = binding.ivCircule
        val ivCircule2 = binding.ivCircule2
        val ivCircule3 = binding.ivCircule3
        val ivCircule4 = binding.ivCircule4



        fun bind(introSlide: IntroSlide) {
            binding.textTitle.text = introSlide.title
            binding.textDescription.text = introSlide.description
            binding.imageSlideIcon.setImageResource(introSlide.icon)

         //   onTextPassed?.invoke(binding.textTitle)
        }
    }
}