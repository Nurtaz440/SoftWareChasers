package demo.app.demotechno.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import demo.app.demotechno.adapter.CategoriesAdapter
import demo.app.demotechno.constants.Constants
import demo.app.demotechno.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!
    val myAdapter by lazy { CategoriesAdapter(requireContext(),{

    }) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

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