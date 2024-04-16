package demo.app.demotechno.vr

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentVrDetailsBinding

class VrDetailsFragment : Fragment() {
    private var _binding: FragmentVrDetailsBinding? = null
    val binding get() = _binding!!

    private val TAG = "MainActivity::class.java.getSimpleName()"
    private val MIN_OPENGL_VERSION = 3.0
    private lateinit var arFragment: ArFragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVrDetailsBinding.inflate(layoutInflater)
        return  (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!checkIsSupportedDeviceOrFinish(requireActivity())) {
            return;
        }

       val fragment =  requireActivity().supportFragmentManager.findFragmentById(R.id.ux_fragment)

        // Check if the fragment is not null and is an instance of ArFragment
        if (fragment != null && fragment is ArFragment) {
            // If it is, assign it to arFragment
            arFragment = fragment
            arFragment.setOnTapArPlaneListener { hitResult: HitResult, plane: Plane, motionEvent: MotionEvent ->
                val anchor = hitResult.createAnchor()
                placeObject(arFragment, anchor, Uri.parse("assets/saucepan.sfb"))//saucepan.sfb

            }
        } else {
            // Handle the case where the fragment is null or not an instance of ArFragment
            // You can log an error, show a message to the user, or handle it in any appropriate way.
            Log.e("VrDetailsFragment", "ArFragment not found or not instance of ArFragment")
            // For example, you might want to disable AR functionality or show an error message.
        }


    }
    private fun placeObject(arFragment: ArFragment, anchor: Anchor, uri: Uri) {
        ModelRenderable.builder()
            .setSource(arFragment.context, uri)
            .build()
            .thenAccept({ modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable) })
            .exceptionally { throwable ->
                Toast.makeText(arFragment.context, "Error:" + throwable.message, Toast.LENGTH_LONG)
                    .show()
                null
            }

    }

    private fun addNodeToScene(arFragment: ArFragment, anchor: Anchor, renderable: Renderable) {
        val anchorNode = AnchorNode(anchor)
        val node = TransformableNode(arFragment.transformationSystem)
        node.renderable = renderable
        node.setParent(anchorNode)
        arFragment.arSceneView.scene.addChild(anchorNode)
        node.select()
    }
    /**
     * Returns false and displays an error message if Sceneform can not run, true if Sceneform can run
     * on this device.
     *
     *
     * Sceneform requires Android N on the device as well as OpenGL 3.0 capabilities.
     *
     *
     * Finishes the activity if Sceneform can not run
     */
    fun checkIsSupportedDeviceOrFinish(activity: Activity): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later")
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        val openGlVersionString =
            (activity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager)
                .deviceConfigurationInfo
                .glEsVersion
        if (java.lang.Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later")
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                .show()
            activity.finish()
            return false
        }
        return true
    }
}