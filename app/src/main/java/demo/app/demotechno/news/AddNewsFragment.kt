package demo.app.demotechno.news

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentAddNewsBinding
import demo.app.demotechno.model.News
import java.util.Date

class AddNewsFragment : Fragment() {
    private var _binding: FragmentAddNewsBinding? = null
    val binding get() = _binding!!
    //Firebase
    private val db = FirebaseFirestore.getInstance()
    private val collectionReference = db.collection("News")

    //Firebase Auth
    private var storageReference: StorageReference? = null

    private var currentUserId: String? = null
    private var currentUserName: String? = null
    private var firebaseAuth: FirebaseAuth? = null
    private val authStateListener: AuthStateListener? = null
    private var user: FirebaseUser? = null

    //using Activity rsult launcher
    var mTakePhoto: ActivityResultLauncher<String>? = null

    var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddNewsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        //Firebase
        storageReference = FirebaseStorage.getInstance()
            .reference
        //firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        binding.progressBar.setVisibility(View.GONE)

        //geting current user
        if (user != null) {
            currentUserId = user!!.uid
            currentUserName = user!!.displayName
        }
        binding.postSaving.setOnClickListener(View.OnClickListener { saveJournal() })

        mTakePhoto = registerForActivityResult<String, Uri>(
            ActivityResultContracts.GetContent()
        ) { o ->
            binding.postImageView.setImageURI(o)

            //get the image uri
            imageUri = o
        }

        binding.postCameraButton.setOnClickListener(View.OnClickListener { //geting image from the Gallery
            mTakePhoto!!.launch("image/*")
        })
    }
    override fun onStart() {
        super.onStart()
        user = firebaseAuth!!.currentUser
    }

    private fun saveJournal() {
        val title: String = binding.postTitleEt.getText().toString().trim()
        binding.progressBar.setVisibility(View.VISIBLE)
        if (!TextUtils.isEmpty(title)  && imageUri != null) {
            // the saving path of the images in firebase Storage
            val filepath = storageReference!!.child("news_images")
                .child("my_news_" + Timestamp.now().seconds)

            //upload image
            filepath.putFile(imageUri!!)
                .addOnSuccessListener {
                    filepath.downloadUrl.addOnSuccessListener { uri ->
                        val imageURL = uri.toString()
                        // Creating a new Journal object
                        val journal = News()
                        journal.title = (title)
                        journal.imageUrl = (imageURL)
                        journal.timestamp = (Timestamp(Date()))


                        //
                        collectionReference.add(journal)
                            .addOnSuccessListener {
                                binding.progressBar.setVisibility(View.GONE)
                                findNavController().navigate(R.id.action_addNewsFragment_to_profileFragment)
                            }.addOnFailureListener { e ->
                                Toast.makeText(
                                    requireContext(),
                                    "Failed: " + e.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Failed: " + e.message, Toast.LENGTH_SHORT)
                        .show()
                    binding.progressBar.setVisibility(View.GONE)
                }
        } else {
            binding.progressBar.setVisibility(View.GONE)
        }
    }

}