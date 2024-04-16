package demo.app.demotechno.exchange

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.vr.sdk.widgets.pano.VrPanoramaView
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentViewVRBinding


class ViewVRFragment : Fragment() {
    private var _binding : FragmentViewVRBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        _binding = FragmentViewVRBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadPanoramaImage()
    }
    private fun loadPanoramaImage() {
        val options = VrPanoramaView.Options()
        try {
            options.inputType = VrPanoramaView.Options.TYPE_MONO
            binding.viewPanaroma.loadImageFromBitmap(BitmapFactory.decodeResource(resources, R.drawable.maracana_map), options)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}