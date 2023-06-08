package demo.app.demotechno.statics

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import demo.app.demotechno.R


class StaticsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.statistics_color)
        return inflater.inflate(R.layout.fragment_statics, container, false)
    }
}