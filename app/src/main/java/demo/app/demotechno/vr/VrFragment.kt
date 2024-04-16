package demo.app.demotechno.vr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentHomeBinding
import demo.app.demotechno.databinding.FragmentVrBinding

class VrFragment : Fragment() {
    private var _binding: FragmentVrBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.vr_color)
        _binding = FragmentVrBinding.inflate(layoutInflater)
        return  (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvBiology.setOnClickListener {
            findNavController().navigate(R.id.action_vrFragment_to_biologyVRFragment)
        }
         binding.cvMath.setOnClickListener {
            findNavController().navigate(R.id.action_vrFragment_to_mathVRFragment)
        }
         binding.cvGeography.setOnClickListener {
            findNavController().navigate(R.id.action_vrFragment_to_geographyVRFragment)
        }
         binding.cvChemistry.setOnClickListener {
            findNavController().navigate(R.id.action_vrFragment_to_vrDetailsFragment)
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
}