package com.example.kochat.network

import com.example.kochat.model.Message
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatService {
    @GET("msg")
    fun getMessage(): Call<List<Message>>

    @POST("msg")
    fun sendMessage(@Body msgBody: MsgBody): Call<List<Message>>
}