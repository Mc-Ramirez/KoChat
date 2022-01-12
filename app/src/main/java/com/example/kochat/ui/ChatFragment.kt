package com.example.kochat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kochat.databinding.FragmentChatBinding
import com.example.kochat.model.Message
import com.example.kochat.network.ChatService
import com.example.kochat.network.MsgBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ChatFragment : Fragment() {
    private val adapter = ChatAdapter()
    private var _binding: FragmentChatBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.rvMsg.adapter = adapter
        binding.rvMsg.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
        val message: MutableList<Message> = mutableListOf()
        message.add(Message(1, 1, "Hola", "1/1/2022"))
        message.add(Message(2, 2, "adios", "1/2/2022"))
        message.add(Message(3, 3, "Hi", "5/2/2022"))
        message.add(Message(4, 4, "bye", "1/3/2022"))
        adapter.submitList(message)

        binding.btnSend.setOnClickListener{
            sendMessage()
        }

        getMessage()
    }

    private fun getMessage() {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()


            val retrofit = Retrofit.Builder()
                .baseUrl("")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ChatService::class.java)

            service.getMessage().enqueue(object : Callback<List<Message>> {
                override fun onResponse(
                    call: Call<List<Message>>,
                    response: Response<List<Message>>
                ) {
                    if(response.isSuccessful){
                        adapter.submitList(response.body())
                    }else{
                        Toast.makeText(context, "error al recibir", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun sendMessage(){
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.1200.80:3000/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ChatService::class.java)

        val myMsg = binding.etMsg.text.toString()
        val myDate = Date().time.toString()
        val msgBody = MsgBody(2, myMsg, myDate)

        service.sendMessage(msgBody).enqueue(object : Callback<List<Message>>{
            override fun onResponse(
                call: Call<List<Message>>,
                response: Response<List<Message>>
            ) {
                if(response.isSuccessful){
                    adapter.submitList(response.body())
                }else{
                    Toast.makeText(context, "error al enviar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Message>>, t: Throwable) {
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
