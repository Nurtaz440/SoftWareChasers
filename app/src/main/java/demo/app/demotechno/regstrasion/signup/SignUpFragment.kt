package demo.app.demotechno.regstrasion.signup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentSignInBinding
import demo.app.demotechno.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvName.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvNumber.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvLastName.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvPasswordConf.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvEmail.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))
        binding.cvPassword.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.edit_text_shadow))

        binding.buttonNext.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
    }
}