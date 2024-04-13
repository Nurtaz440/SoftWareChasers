package demo.app.demotechno.profile

import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!


    private lateinit var viewModel: ProfileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cvAdd.setOnClickListener {
            chooseImageFromGallery()
        }
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        // Observe user information and update UI accordingly
        viewModel.userInfo.observe(viewLifecycleOwner, Observer { userInfo ->
            userInfo?.let {
                binding.tvName.text = "${it.name}"
                binding.tvNumber.text = "${it.number}"
            }
        })
        loadImageFromFirebase(binding.ivProfile)
        binding.cvPhoto.setOnClickListener {
            val dialog = Dialog(requireActivity())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            dialog.setContentView(R.layout.dialog)

            val profileImage = dialog.findViewById<View>(R.id.iv_profile) as ImageView
            loadImageFromFirebase(profileImage)
            
            profileImage.setOnClickListener { 
                dialog.dismiss()
            }
            
            dialog.show()
        }

        binding.logOut.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())

            // set message of alert dialog
            dialogBuilder
                // if the dialog is cancelable
                .setCancelable(false)
                // positive button text and action
                .setPositiveButton(
                    "Ok",
                    DialogInterface.OnClickListener { dialog, id ->
                        viewModel.signOut()

                        findNavController().navigate(R.id.signInFragment)

                    })
                // negative button text and action
                .setNegativeButton(
                    "Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("Leave of Your Account?")

            // show alert dialog
            alert.show()

        }
        binding.llAddNews.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_addNewsFragment)
        }

    }
    private val PICK_IMAGE_REQUEST = 1

    fun chooseImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri = data.data!!
            // Now you have the image URI, you can upload it to Firebase Storage
            binding.ivProfile.setImageURI(imageUri)
            uploadImageToFirebase(imageUri)
        }
    }
    fun uploadImageToFirebase(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("images/${FirebaseAuth.getInstance().currentUser?.uid}")

        val uploadTask = imagesRef.putFile(imageUri)

        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image uploaded successfully
            // Now you can retrieve the download URL
            imagesRef.downloadUrl.addOnSuccessListener { uri ->
                // Now you have the download URL, you can use it to display the image or store it
                val imageUrl = uri.toString()
                // Save this URL to Firebase Database or load it into ImageView
                // For example, if you're using Firebase Realtime Database:
                FirebaseDatabase.getInstance().getReference("users/${FirebaseAuth.getInstance().currentUser?.uid}/profileImage")
                    .setValue(imageUrl)
            }
        }.addOnFailureListener { exception ->
            // Handle unsuccessful upload
        }
    }
    fun loadImageFromFirebase(imageView: ImageView) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imagesRef = storageRef.child("images/${FirebaseAuth.getInstance().currentUser?.uid}")

        imagesRef.downloadUrl.addOnSuccessListener { uri ->
            // Load the image into ImageView using a library like Glide or Picasso
            Glide.with(this)
                .load(uri)
                .into(imageView)
        }.addOnFailureListener { exception ->
            // Handle failed download
        }
    }

}