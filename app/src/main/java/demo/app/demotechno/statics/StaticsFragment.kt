package demo.app.demotechno.statics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentStaticsBinding


class StaticsFragment : Fragment() {
    private var _binding: FragmentStaticsBinding? = null
    val binding get() = _binding!!
    var myViewModel: MyViewModel? = null
    private var adapter: ChatAdapter? = null
    private var messageList: List<ChatMessage>? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.statistics_color)
        _binding = FragmentStaticsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]



        //recyclerview
        val linearLayoutManager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
        //linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.stackFromEnd = true
        binding.recyclerView.setLayoutManager(linearLayoutManager)
        binding.recyclerView.setHasFixedSize(true)

        val groupName = "book"
        myViewModel!!.getMessagesLiveData(groupName)
            .observe(viewLifecycleOwner, object : Observer<List<ChatMessage?>> {
               override fun onChanged(chatMessages: List<ChatMessage?>) {
                    messageList = ArrayList()
                   (messageList!! as ArrayList<ChatMessage>).addAll((chatMessages.filterNotNull()))
                    adapter = ChatAdapter(messageList!!, requireContext())
                   binding.recyclerView.setAdapter(adapter)
                    adapter!!.notifyDataSetChanged()

                    //scroll to the latest message added
                    val latestPosition = adapter!!.itemCount - 1
                    if (latestPosition > 0) {
                        binding.recyclerView.smoothScrollToPosition(latestPosition)
                    }
                }
            })

        binding.btnSend.setOnClickListener(View.OnClickListener {
            val msg = binding.edittextChatMeassage.text.toString()
            myViewModel!!.sendMessage(msg, groupName)
            binding.edittextChatMeassage.text.clear()
        })
    }
}