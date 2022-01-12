package com.example.kochat.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("msgId")
    val msgId: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("text")
    val msg: String,
    @SerializedName("date")
    val date: String
)
