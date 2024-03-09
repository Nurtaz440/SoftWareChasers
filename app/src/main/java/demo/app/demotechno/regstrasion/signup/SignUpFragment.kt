package demo.app.demotechno.regstrasion.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentSignInBinding
import demo.app.demotechno.databinding.FragmentSignUpBinding
import demo.app.demotechno.utils.SharedPreferencesHelper

class SignUpFragment : Fragment() {
    private var _binding : FragmentSignUpBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: RegistrationViewModel
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

        viewModel = ViewModelProvider(this).get(RegistrationViewModel::class.java)

        // Initially, the button is disabled
        binding.buttonNext.isEnabled = false

        // Add TextWatcher to monitor changes in EditText fields
        binding.evEmail.addTextChangedListener(textWatcher)
        binding.evPassword.addTextChangedListener(textWatcher)
        binding.evConfirmPassword.addTextChangedListener(textWatcher)
        binding.evName.addTextChangedListener(textWatcher)
        binding.evNumber.addTextChangedListener(textWatcher)
        binding.evSurname.addTextChangedListener(textWatcher)

//        binding.buttonNext.setOnClickListener {
//            val email = binding.evEmail.text.toString()
//            val password = binding.evPassword.text.toString()
//            val number = binding.evNumber.text.toString()
//            val name = binding.evName.text.toString()
//            viewModel.registerUser(email, password,number,name)
//
//        }
        viewModel.registrationResult.observe(viewLifecycleOwner, { isSuccess ->
            if (isSuccess) {
                binding.imagePromptProgress.visibility = View.GONE
                // Registration successful, handle accordingly (e.g., navigate to next screen)
                findNavController().navigate(R.id.homeFragment)
                SharedPreferencesHelper.setUserSignedIn(requireContext(), true)

            } else {
                binding.imagePromptProgress.visibility = View.GONE
                // Registration failed, show error message to the user
                Toast.makeText(context,"Try again",Toast.LENGTH_SHORT).show()
            }
        })
    }
    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            // Check if both EditText fields are not empty
            val email = binding.evEmail.text.toString().trim()
            val password = binding.evPassword.text.toString().trim()
            val number = binding.evNumber.text.toString()
            val name = binding.evName.text.toString()
            val surname = binding.evSurname.text.toString()
            val confPass = binding.evConfirmPassword.text.toString()
            val enableButton = email.isNotEmpty() && password.isNotEmpty()
                    && number.isNotEmpty()  && name.isNotEmpty()  && surname.isNotEmpty()
                    && confPass.isNotEmpty()
            // Enable or disable button based on EditText fields
            if (enableButton){
                binding.buttonNext.isEnabled = true
                binding.buttonNext.setOnClickListener {
                    viewModel.registerUser(email, password,number,name)
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