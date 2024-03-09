package demo.app.demotechno.regstrasion.signin

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentSignInBinding
import demo.app.demotechno.utils.SharedPreferencesHelper

class SignInFragment : Fragment() {

    private var _binding : FragmentSignInBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel
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
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        // Initially, the button is disabled
        binding.buttonNext.isEnabled = false

        // Add TextWatcher to monitor changes in EditText fields
        binding.evEmail.addTextChangedListener(textWatcher)
        binding.evPassword.addTextChangedListener(textWatcher)


        viewModel.signInResult.observe(viewLifecycleOwner, Observer { success ->
            if (success) {
                // Sign-in successful, navigate to home page
                // Replace HomeActivity::class.java with your home page activity
                binding.imagePromptProgress.visibility = View.GONE
               findNavController().navigate(R.id.homeFragment)
                SharedPreferencesHelper.setUserSignedIn(requireContext(), true)
            } else {
                // Sign-in failed, show error message to the user
             binding.tvError.text = viewModel.errorMessage.value
                binding.imagePromptProgress.visibility = View.GONE
            }
        })

    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // Check if both EditText fields are not empty
            val email = binding.evEmail.text.toString().trim()
            val password = binding.evPassword.text.toString().trim()
            val enableButton = email.isNotEmpty() && password.isNotEmpty()

            // Enable or disable button based on EditText fields
            if (enableButton){
                binding.buttonNext.isEnabled = true
                binding.buttonNext.setOnClickListener {
                    viewModel.signInWithEmailAndPassword(email, password)
                    binding.imagePromptProgress.visibility = View.VISIBLE
                }
            }else{
                binding.buttonNext.isEnabled = false
            }

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // Not needed
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Not needed
        }
    }
}