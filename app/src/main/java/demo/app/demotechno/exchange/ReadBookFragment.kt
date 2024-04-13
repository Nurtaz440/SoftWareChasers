package demo.app.demotechno.exchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentReadBookBinding

class ReadBookFragment : Fragment() {
    private var _binding : FragmentReadBookBinding? = null
    val binding get() = _binding!!

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
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.ivBook.setOnClickListener {
            findNavController().navigate(R.id.action_readBookFragment_to_viewVRFragment)
        }

    }

}