package com.example.composemoviebox.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composemoviebox.BuildConfig
import com.example.composemoviebox.model.MessageModel
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {
    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel: GenerativeModel =
        GenerativeModel(modelName = "gemini-1.5-flash-001", apiKey = BuildConfig.GEMINI_API_KEY)

    fun sendMessage(question: String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat(
                history = messageList.map {
                    if (it.isUser) {
                        content("user"){text(it.message)}
                    }
                    else {
                        content("model"){text(it.message)}
                    }

                }.toList()

            )

            messageList.add(MessageModel(question, true))

            val response = chat.sendMessage(question)

            messageList.add(MessageModel(response.text.toString(), false))
        }
    }
}
