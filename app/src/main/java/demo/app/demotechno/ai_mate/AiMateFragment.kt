package demo.app.demotechno.ai_mate

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ai.client.generativeai.GenerativeModel
import demo.app.demotechno.R
import demo.app.demotechno.chat.ChatData
import demo.app.demotechno.chat.ChatViewModel
import demo.app.demotechno.chat.MessageAdapter
import demo.app.demotechno.databinding.FragmentAiMateBinding

class AiMateFragment : Fragment() {
    private var _binding: FragmentAiMateBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: ChatViewModel

    private val chatAdapter = MessageAdapter()
    private val messageList = mutableListOf<Pair<String, Int>>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.ai_mate_color)
        // Inflate the layout for this fragment
        _binding = FragmentAiMateBinding.inflate(layoutInflater)
        return (binding.root)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val geminiAi  =  GenerativeModel(
            modelName = "gemini-pro",
            apiKey =  ChatData.api_key
        )

        viewModel = ViewModelProvider(
            this,
            ChatViewModel.ChatViewModelFactory(geminiAi))
            .get(ChatViewModel::class.java)

        setAdapter()
        sendMessage()
        observe()

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

    }
    private fun setAdapter(){
        val ll = LinearLayoutManager(requireContext())
        ll.stackFromEnd = true
        binding.rvMain.layoutManager = ll
        binding.rvMain.setHasFixedSize(true)
        binding.rvMain.adapter = chatAdapter
    }
    private fun sendMessage(){
        binding.btnSend.setOnClickListener {
            val userMessage = binding.evMessage.text.toString()
            viewModel.geminiChat(userMessage)
            messageList.add(Pair(userMessage, MessageAdapter.VIEW_TYPE_USER))
            chatAdapter.setMessages(messageList)
            scrollPosition()
            binding.evMessage.setText("")
            val inputMethodManager =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
            binding.imagePromptProgress.visibility = View.VISIBLE
        }
    }

    private fun observe(){
        viewModel.messageResponse.observe(viewLifecycleOwner){content ->
            content.text?.let {
                messageList.add(Pair(it,MessageAdapter.VIEW_TYPE_GEMINI))
                chatAdapter.setMessages(messageList)
                binding.imagePromptProgress.visibility = View.GONE
                scrollPosition()
            }
        }
    }

    private fun scrollPosition(){
        binding.rvMain.smoothScrollToPosition(chatAdapter.itemCount - 1)
    }
}