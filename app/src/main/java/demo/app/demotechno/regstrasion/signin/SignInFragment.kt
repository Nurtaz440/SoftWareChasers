package demo.app.demotechno.regstrasion.signin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding : FragmentSignInBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvEmail.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvPassword.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
    }
}