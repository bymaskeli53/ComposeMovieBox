package com.example.composemoviebox

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun ChatScreen(chatViewModel: ChatViewModel = hiltViewModel()) {
    var userInput by remember { mutableStateOf("") }
    val chatMessages = remember { mutableStateListOf<Pair<String, Boolean>>() }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier =
        Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        // Chat messages
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            reverseLayout = true, // Messages come from bottom to top
        ) {
            items(chatViewModel.messageList.reversed()) { message ->
                ChatBubble(message = message.message, isUser = message.isUser)
            }
        }

        // User input area
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextField(
                value = userInput,
                onValueChange = { userInput = it },
                placeholder = { Text("Enter your prompt...") },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(20.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (userInput.isNotBlank()) {

                       // chatMessages.add(userInput.trim() to true) // User message
                        chatViewModel.sendMessage(userInput)
                       // chatMessages.add("AI Response to '$userInput'" to false) // AI message
                        userInput = "" // Clear input field

                        coroutineScope.launch {
                            listState.animateScrollToItem(0)
                        }
                    }
                },
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(12.dp),
            ) {
                Text("Send")
            }
        }
    }
}

@Composable
fun ChatBubble(
    message: String,
    isUser: Boolean,
) {
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val backgroundColor = if (isUser) Color(0xFFDCF8C6) else Color(0xFFF0F0F0)
    val bubbleShape =
        if (isUser) {
            RoundedCornerShape(16.dp, 0.dp, 16.dp, 16.dp)
        } else {
            RoundedCornerShape(0.dp, 16.dp, 16.dp, 16.dp)
        }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
    ) {
        Box(
            modifier =
                Modifier
                    .background(backgroundColor, shape = bubbleShape)
                    .padding(12.dp)
                    .widthIn(max = 250.dp),
        ) {
            Text(
                text = message,
                color = Color.Black,
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    ChatScreen()
}
