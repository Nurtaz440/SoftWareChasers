package demo.app.demotechno.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import demo.app.demotechno.R
import demo.app.demotechno.databinding.FragmentChatBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class ChatFragment : Fragment() {
    private var _binding: FragmentChatBinding? = null
    val binding get() = _binding!!
    lateinit var messageList: ArrayList<Message>
    private lateinit var messageAdapter: MessageAdapter
    val JSON = "application/json; charset=utf-8".toMediaType()
    var client = OkHttpClient()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
        _binding = FragmentChatBinding.inflate(layoutInflater)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(messageList)
        binding.rvMain.adapter = messageAdapter
        val ll = LinearLayoutManager(requireContext())
        ll.stackFromEnd = true
        binding.rvMain.layoutManager = ll



        binding.btnSend.setOnClickListener {
            val str = binding.messageEditText.text.toString().trim()
            addChat(str, SENT_BY_NAME)

            callApi(str)
        }
    }

    fun addChat(message: String, sentBy: String) {
        messageList.add(Message(message, sentBy))
        messageAdapter.notifyDataSetChanged()
        binding.rvMain.smoothScrollToPosition(messageAdapter.itemCount)

    }

    fun callApi(question: String) {
        //okhttp
        messageList.add(Message("Typing... ", SENT_BY_BOT))
        val jsonObject = JSONObject()
        try {
            jsonObject.put("model", "text-davinci-003")
            jsonObject.put("prompt", question)
            jsonObject.put("max_tokens", 4000)
            jsonObject.put("temperature", 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val body: RequestBody = RequestBody.create(JSON, jsonObject.toString())
        val request: Request = Request.Builder()
            .url("https://api.openai.com/v1/completions")
            .header("Authorization", "Bearer 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")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                addResponse("Failed to load response due to " + e.localizedMessage)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    var jsonObject: JSONObject? = null
                    try {
                        jsonObject = JSONObject(response.body!!.string())
                        val jsonArray = jsonObject.getJSONArray("choices")
                        val result = jsonArray.getJSONObject(0).getString("text")
                        addResponse(result.trim { it <= ' ' })
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                } else {
                    addResponse("Failed to load response due to " + response.body!!.toString())
                }
            }
        })
    }

    fun addResponse(response: String?) {
        messageList.removeAt(messageList.size - 1)
        addToChat(response, SENT_BY_BOT)
    }

    fun addToChat(message: String?, sentBy: String?) {
        requireActivity().runOnUiThread {
            messageList.add(Message(message!!, sentBy!!))
            messageAdapter.notifyDataSetChanged()
            binding.rvMain.smoothScrollToPosition(messageAdapter.itemCount)
        }
    }
}