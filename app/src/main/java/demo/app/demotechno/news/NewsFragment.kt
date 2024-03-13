package demo.app.demotechno.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.R
import demo.app.demotechno.adapter.NewsAdapter
import demo.app.demotechno.databinding.FragmentNewsBinding
import demo.app.demotechno.databinding.FragmentProfileBinding
import demo.app.demotechno.model.News
import demo.app.demotechno.utils.Constants

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    val binding get() = _binding!!
    var arrayList = ArrayList<News>()
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.news_color)
        _binding = FragmentNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList.addAll(Constants.getNewsItems())
        newsAdapter = NewsAdapter(requireContext(),arrayList)

        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
            setHasFixedSize(true)
            adapter = newsAdapter
            newsAdapter.notifyDataSetChanged()
        }
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}