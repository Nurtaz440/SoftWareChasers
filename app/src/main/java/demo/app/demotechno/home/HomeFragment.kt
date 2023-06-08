package demo.app.demotechno.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import demo.app.demotechno.R
import demo.app.demotechno.adapter.CategoriesAdapter
import demo.app.demotechno.constants.Constants
import demo.app.demotechno.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!
    val myAdapter by lazy { CategoriesAdapter(requireContext()) {
        when (it) {
            0 -> findNavController().navigate(R.id.action_homeFragment_to_ebooksFragment)
            1 -> findNavController().navigate(R.id.action_homeFragment_to_readBookFragment)
            2 -> findNavController().navigate(R.id.action_homeFragment_to_vrFragment)
            3 -> findNavController().navigate(R.id.action_homeFragment_to_newsFragment)
            4 -> findNavController().navigate(R.id.action_homeFragment_to_coursesFragment)
            5 -> findNavController().navigate(R.id.action_homeFragment_to_aiMateFragment)
        }
    }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        requireActivity().window.statusBarColor =
//            ContextCompat.getColor(requireContext(), R.color.e_books_color)
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return  (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter.setData(Constants.getIcons())
        binding.rvGird.apply {
            layoutManager = GridLayoutManager(requireContext(),3)
            setHasFixedSize(true)
            adapter = myAdapter
        }
    }
}