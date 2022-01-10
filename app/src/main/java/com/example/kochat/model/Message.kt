package com.example.kochat.model

data class Message(
    val userId: Int,
    val msgId: Int,
    val msg: String,
    val createDate: String
)
