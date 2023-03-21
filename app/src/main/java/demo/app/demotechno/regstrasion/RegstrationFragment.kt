package demo.app.demotechno.regstrasion

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentRegstrationBinding
import demo.app.demotechno.regstrasion.viewpager.RegisterViewPager


class RegstrationFragment : Fragment() {

    private var _binding : FragmentRegstrationBinding? = null
    val binding get() = _binding!!
    val regstationViewPager by lazy { RegisterViewPager(requireFragmentManager(),requireActivity().lifecycle) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Inflate the layout for this fragment
        _binding = FragmentRegstrationBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  binding.tabLayout.setSelectedTabIndicatorColor(resources.getColor(R.color.deep_sky_blue_400))
        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("SIGN IN"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("SIGN UP"))
        binding.viewPager.adapter = regstationViewPager
        binding.viewPager.offscreenPageLimit = 1



        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                binding.viewPager.currentItem=tab?.position!!
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position))
                getStyle()
                if (position == regstationViewPager.itemCount - 1) {
                    getStyle()
                }else{
                    getStyle()
                }
            }
        })

    }
    fun getStyle(){

        when (binding.viewPager.currentItem) {
            0 -> {
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.purple_500)
                binding.cvRegister.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.cv_back_first
                    )
                )

                binding.ivCircule.visibility = View.VISIBLE
                binding.ivCircule2.visibility = View.VISIBLE
                binding.ivCircule3.visibility = View.GONE
                binding.ivCircule4.visibility = View.GONE

            }
            1 -> {
                activity?.window?.statusBarColor =
                    ContextCompat.getColor(requireContext(), R.color.boarding_color_second)
                binding.cvRegister.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.cv_back_second
                    )
                )
                binding.ivCircule.visibility = View.GONE
                binding.ivCircule2.visibility = View.GONE
                binding.ivCircule3.visibility = View.VISIBLE
                binding.ivCircule4.visibility = View.VISIBLE

            }
        }
    }
}