package demo.app.demotechno.ai_mate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import demo.app.demotechno.R

class AiMateFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.ai_mate_color)
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ai_mate, container, false)
    }
}