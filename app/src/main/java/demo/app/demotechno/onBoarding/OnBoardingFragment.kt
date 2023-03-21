package demo.app.demotechno.onBoarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import demo.app.demotechno.R
import demo.app.demotechno.adapter.IntroSliderAdapter
import demo.app.demotechno.constants.Constants
import demo.app.demotechno.databinding.FragmentOnBoardingBinding
import demo.app.demotechno.model.IntroSlide


class OnBoardingFragment : Fragment() {
    private var binding: FragmentOnBoardingBinding? = null

    private val introSliderAdapter by lazy {  IntroSliderAdapter(requireContext()) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.purple_500)
        binding = FragmentOnBoardingBinding.inflate(layoutInflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        introSliderAdapter.setData(Constants.getOnBoardingList())

        binding?.viewPager?.adapter = introSliderAdapter
//sets the viewpager2 to the indicator
        binding?.indicator?.setViewPager(binding?.viewPager)

        binding?.viewPager?.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

/*
*check if its the last page, change text on the button
*from next to finish and set the click listener to
*to navigate to welcome screen else set the text to next
* and click listener to move to next page
*/
                    if (position == introSliderAdapter.itemCount - 1) {
//this animation is added to the finish button
                        getColor()
                        binding?.buttonNext?.text = "Start"
                        binding?.buttonNext?.setOnClickListener {

                            findNavController().navigate(R.id.action_onBoardingFragment_to_regstrationFragment)

                        }
                    } else {
                        getColor()
                        binding?.buttonNext?.text = "Next"
                        binding?.buttonNext?.setOnClickListener {
                            binding?.viewPager?.currentItem?.let {
                                binding?.viewPager?.setCurrentItem(it + 1, false)
                               getColor()
                            }

                        }
                    }
                }
            })

        binding?.viewPager?.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
    fun getColor(){
        when(binding?.viewPager?.currentItem){
            0,2,4 -> {
                activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.purple_500)
                binding!!.buttonNext.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.main_color))
            }
            1,3,5 -> {
                activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(),R.color.boarding_color_second)
                binding!!.buttonNext.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.btn_color))
            }
        }
    }
}