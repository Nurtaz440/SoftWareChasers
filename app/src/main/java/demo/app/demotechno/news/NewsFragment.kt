package demo.app.demotechno.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import demo.app.demotechno.R
import demo.app.demotechno.adapter.NewsAdapter
import demo.app.demotechno.databinding.FragmentNewsBinding
import demo.app.demotechno.model.News

class NewsFragment : Fragment() {
    private var _binding: FragmentNewsBinding? = null
    val binding get() = _binding!!


    //firebaseAuth
    private var firebaseAuth: FirebaseAuth? = null
    private val authStateListener: AuthStateListener? = null
    private var user: FirebaseUser? = null

    //Firebase FireStore
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("News")

    //Firebase Storage
    private val storageReference: StorageReference? = null

    //List of Journals
    private var journalList: ArrayList<News>? = null

    private var myAdapter: NewsAdapter? = null
    private var news :News? = null
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

        //firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth!!.getCurrentUser()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    override fun onStart() {
        super.onStart()
        collectionReference.get()
            .addOnSuccessListener { queryDocumentSnapshots -> // queryDocumentSnapshot: is an object that represents
                // a single document retrieved from a Firestore query
                // QueryDocumentSnapshot --> Document
                //postArrayList
                journalList = ArrayList()
                for (journal in queryDocumentSnapshots) {
                    val journal1: News = journal.toObject(News::class.java)
                    this.news= journal1
                    journalList!!.add(journal1)

                }
                //recyclerview
                binding.rvNews.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
                binding.rvNews.setHasFixedSize(true)

                myAdapter = NewsAdapter(requireContext(), journalList!!,{ position->

                    val action = NewsFragmentDirections.actionNewsFragmentToNewsDetailFragment(journalList!![position])
                    findNavController().navigate(action)
                })
                binding.rvNews.adapter = myAdapter
                myAdapter!!.notifyDataSetChanged()
            }.addOnFailureListener {
                Toast.makeText(
                    requireContext(),
                    "something went wrong!!",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}