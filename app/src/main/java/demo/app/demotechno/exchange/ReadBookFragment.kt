package demo.app.demotechno.exchange

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import demo.app.demotechno.databinding.FragmentReadBookBinding
import demo.app.demotechno.exchange.viewpager.ExchangeViewPager
import demo.app.demotechno.regstrasion.viewpager.RegisterViewPager

class ReadBookFragment : Fragment() {
    private var _binding : FragmentReadBookBinding? = null
    val binding get() = _binding!!
    val regstationViewPager by lazy { ExchangeViewPager(requireFragmentManager(),requireActivity().lifecycle) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        // Inflate the layout for this fragment
        _binding = FragmentReadBookBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.apply {
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("ID"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("SCAN ID"))
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

            }
        })

    }

}