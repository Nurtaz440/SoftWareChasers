package demo.app.demotechno.profile

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.MediaStore
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentProfileBinding
import java.io.ByteArrayOutputStream
import java.io.File


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    val binding get() = _binding!!
    val PICK_IMAGE = 1
    val READ_EXTERNAL_STORAGE_REQUEST_CODE = 2
    val PRODUCT_PHOTO = "photo"
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
        getDefaults(PRODUCT_PHOTO, requireContext())
        binding.cvAdd.setOnClickListener {
            pickImage()
        }
    }
    @SuppressLint("IntentReset")
    private fun pickImage() {
        if (ActivityCompat.checkSelfPermission(requireContext(), READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                .setType("image/*")

            startActivityForResult(intent, PICK_IMAGE)
        }else{
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                PICK_IMAGE
            )
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){

            val uri = data?.data
            if (uri != null) {
                val imageFile = uriToImageFile(uri)
                val myBitmap = BitmapFactory.decodeFile(imageFile!!.getAbsolutePath())
                binding.ivProfile.setImageBitmap(myBitmap)
                val str_bitmap = BitMapToString(myBitmap)
                setDefaults(PRODUCT_PHOTO, str_bitmap, requireContext())
            }
            if (uri != null) {
                val imageBitmap = uriToBitmap(uri)
                binding.ivProfile.setImageBitmap(imageBitmap)
                val str_bitmap = BitMapToString(imageBitmap)
                setDefaults(PRODUCT_PHOTO, str_bitmap, requireContext())
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // pick image after request permission success
                    pickImage()
                }
            }
        }
    }
    private fun uriToImageFile(uri: Uri): File? {
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, filePathColumn, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                val filePath = cursor.getString(columnIndex)
                cursor.close()
                return File(filePath)
            }
            cursor.close()
        }
        return null
    }

    private fun uriToBitmap(uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, uri)
    }
    fun setDefaults(str_key: String?, value: String?, context: Context?) {
        val shre = PreferenceManager.getDefaultSharedPreferences(context)
        val edit = shre.edit()
        edit.putString(str_key, value)
        edit.apply()
    }
    fun getDefaults(key: String?, context: Context?):String? {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(key, null)
    }
    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val arr: ByteArray = baos.toByteArray()
        return Base64.encodeToString(arr, Base64.DEFAULT)
    }

}