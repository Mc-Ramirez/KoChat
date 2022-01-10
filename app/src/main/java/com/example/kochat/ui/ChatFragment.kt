package com.example.kochat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kochat.databinding.FragmentChatBinding
import com.example.kochat.model.Message

class ChatFragment : Fragment() {
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
        val adapter = ChatAdapter()
        binding.rvMsg.adapter = adapter
        binding.rvMsg.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, true)
        val message: MutableList<Message> = mutableListOf()
        message.add(Message(1, 1, "Hola", "1/1/2022"))
        message.add(Message(2, 2, "adios", "1/2/2022"))
        message.add(Message(3, 3, "Hi", "5/2/2022"))
        message.add(Message(4, 4, "bye", "1/3/2022"))
        adapter.submitList(message)

        binding.btnSend.setOnClickListener{
            val msgSend = binding.etMsg.text
            message.add(0, Message(4,7,msgSend.toString(), "20/3/2022"))
            adapter.submitList(message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
