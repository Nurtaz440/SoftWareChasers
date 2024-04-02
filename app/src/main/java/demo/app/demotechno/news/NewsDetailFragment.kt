package demo.app.demotechno.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import demo.app.demotechno.databinding.FragmentNewsDetailBinding


class NewsDetailFragment : Fragment() {
    private var _binding: FragmentNewsDetailBinding? = null
    val binding get() = _binding!!

    val itemarg : NewsDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsDetailBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      //  binding.ivImage.setImageResource(itemarg.newsArg.imageUrl)

        //Glide
        Glide.with(requireActivity())
            .load(itemarg.newsArg.imageUrl)
            .fitCenter()
            .into(binding.ivImage)

        binding.tvName.setText(itemarg.newsArg.title)
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}