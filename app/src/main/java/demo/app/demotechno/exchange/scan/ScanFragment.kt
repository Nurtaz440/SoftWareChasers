package demo.app.demotechno.exchange.scan

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.zxing.integration.android.IntentIntegrator
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentReadBookBinding
import demo.app.demotechno.databinding.FragmentScanBinding

class ScanFragment : Fragment() {
    private var _binding : FragmentScanBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentScanBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnQr.setOnClickListener {
            val intentIntegrator = IntentIntegrator(requireActivity())
            intentIntegrator.setOrientationLocked(true)
            intentIntegrator.setPrompt("Scan QR code")
            intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            intentIntegrator.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (intentResult != null){
            val contents = intentResult.contents
            if (contents != null){
                binding.tvQr.setText(intentResult.contents)
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

}