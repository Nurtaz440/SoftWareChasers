package demo.app.demotechno.ebooks

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rajat.pdfviewer.PdfViewerActivity
import demo.app.demotechno.R
import demo.app.demotechno.adapter.EBoosAdapter
import demo.app.demotechno.constants.Constants
import demo.app.demotechno.databinding.FragmentEbooksBinding
import demo.app.demotechno.model.EbooksModel


class EbooksFragment : Fragment() {
    private var _binding: FragmentEbooksBinding? = null
    val binding get() = _binding!!
    private val requiredPermissionList = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var download_file_url ="https://ncca.ie/media/2472/up_and_away.pdf"
        //"https://github.com/afreakyelf/afreakyelf/raw/main/Log4_Shell_Mid_Term_final.pdf"
    var per = 0f
    private val PERMISSION_CODE = 4040

    val myAdapter by lazy { EBoosAdapter(requireContext(),{
       // findNavController().navigate(R.id.action_ebooksFragment_to_readBookFragment)

        pdfShow()
    }) }
    private var mItems: ArrayList<EbooksModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEbooksBinding.inflate(layoutInflater)
        return  (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = GridLayoutManager(context, 2)
        requireActivity().window!!.statusBarColor = ContextCompat.getColor(requireContext(),R.color.main_color)
        mItems = ArrayList()
        mItems!!.add(
            EbooksModel(
                R.drawable.image_cv,
                R.drawable.five,
                R.color.text1,
                R.color.cv_back,
                "grade"
            )
        )
        mItems!!.add(
            EbooksModel(
                R.drawable.image_cv2,
                R.drawable.six,
                R.color.text2,
                R.color.cv_back2,
                "grade"
            )
        )
        mItems!!.add(
            EbooksModel(R.drawable.image_cv3,R.drawable.seven,R.color.text3, R.color.cv_back3, "grade")
        )
        mItems!!.add(
            EbooksModel(R.drawable.image_cv4,R.drawable.eight,R.color.text4,  R.color.cv_back4,"grade")
        )
        mItems!!.add(
            EbooksModel(R.drawable.image_cv5,R.drawable.nine, R.color.text5, R.color.cv_back5,"grade")
        )
        mItems!!.add(
            EbooksModel(R.drawable.image_cv6,R.drawable.ten, R.color.text6, R.color.cv_back6,"grade")
        )
        mItems!!.add(
            EbooksModel(R.drawable.image_cv7,R.drawable.eleven, R.color.text7, R.color.cv_back7,"grade")
        )
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (mItems!!.size % 2 != 0) {
                    if (position == mItems!!.size - 1) 2 else 1
                } else {
                    1
                }
            }
        }
        myAdapter.setData(mItems!!)
        binding.rvEBooks.apply {
            setHasFixedSize(true)
            adapter = myAdapter
        }

        binding.rvEBooks.setLayoutManager(layoutManager)
    }
    fun pdfShow(){
        if (checkAndRequestPermission()){
            launchPdf()
        }
    }
    private fun launchPdf() {
        startActivity(
            PdfViewerActivity.launchPdfFromUrl(           //PdfViewerActivity.Companion.launchPdfFromUrl(..   :: incase of JAVA
                context,
                pdfUrl = download_file_url,                                // PDF URL in String format
                pdfTitle = "English",                        // PDF Name/Title in String format
                "books",                  // If nothing specific, Put "" it will save to Downloads
                enableDownload = true                    // This param is true by defualt.
            )
        )

        //path
//            PdfViewerActivity.launchPdfFromPath(
//                context = requireContext(),   "math_9_class.pdf",
//                pdfTitle = "Title", directoryName = "assets",true,  true)
        //)
    }
    private fun checkAndRequestPermission(): Boolean {
        val permissionsNeeded = ArrayList<String>()

        for (permission in requiredPermissionList) {
            if (ContextCompat.checkSelfPermission(requireActivity(), permission) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                permissionsNeeded.add(permission)
            }
        }

        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                permissionsNeeded.toTypedArray(),
                PERMISSION_CODE
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> if (grantResults.isNotEmpty()) {
                val readPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readPermission && writePermission)
                    launchPdf()
                else {
                    Toast.makeText(requireContext(), " Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}