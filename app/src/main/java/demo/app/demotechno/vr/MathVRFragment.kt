package demo.app.demotechno.vr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentMathVRBinding
import demo.app.demotechno.databinding.FragmentVrDetailsBinding
import io.github.sceneview.ar.node.ArModelNode

class MathVRFragment : Fragment() {
    private var _binding: FragmentMathVRBinding? = null
    val binding get() = _binding!!
    private lateinit var modelNode: ArModelNode


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMathVRBinding.inflate(layoutInflater)
        return  (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        modelNode = ArModelNode().apply {
            loadModelGlbAsync(
                glbFileLocation = "models/math.glb"
            ) {
                binding.sceneView.planeRenderer.isVisible = true
            }
            onAnchorChanged = {
                binding.btnTakePhoto.isGone
            }
        }

        // Add the modelNode to your AR scene
        // For example:
        // scene.addChild(modelNode)
        binding.sceneView.addChild(modelNode)

        binding.btnTakePhoto.setOnClickListener {
            place()
        }
    }
    private fun place(){
        modelNode.anchor()
        binding.sceneView.planeRenderer.isVisible = false
    }
}