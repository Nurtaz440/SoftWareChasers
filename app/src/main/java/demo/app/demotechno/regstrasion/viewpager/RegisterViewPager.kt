package demo.app.demotechno.regstrasion.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import demo.app.demotechno.regstrasion.signin.SignInFragment
import demo.app.demotechno.regstrasion.signup.SignUpFragment

class RegisterViewPager(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifecycle) {

    override fun getItemCount()=2

    override fun createFragment(position: Int): Fragment {
        val fragment=Fragment()
        when(position){
            0-> return SignInFragment()
            1-> return SignUpFragment()
        }
        return fragment
    }
}