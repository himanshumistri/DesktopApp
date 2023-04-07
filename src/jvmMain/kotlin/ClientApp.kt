
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.websocket.*
import io.ktor.http.*

import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow


fun main() = application {
    Window(onCloseRequest = ::exitApplication, transparent = false, title = "Local Chat App") {
        val messages = remember { mutableListOf<String>() }
        val messageInput = remember { mutableStateOf("") }
        val client = remember { HttpClient(CIO) { install(WebSockets) } }
        val chatSocket = remember { MutableStateFlow<WebSocketSession?>(null) }
        val scope = rememberCoroutineScope()

        fun sendMessage() {
            scope.launch {
                chatSocket.value?.send(Frame.Text(messageInput.value))
                println("WebSocket Send messaged is called")
                messageInput.value = ""
            }
        }

        fun connectToChat() {
            scope.launch {
                val socket = client.webSocket(
                    method = HttpMethod.Get,
                    host = "localhost",
                    port = 8080,
                    path = "/chat"
                ) {
                    println("WebSocket Connected")
                    chatSocket.value = this
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val message = frame.readText()
                        messages.add(message)
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.weight(1f).padding(8.dp)
            ) {
                LazyColumn {
                    items(messages) { message ->
                        Text(message, fontSize = 20.sp, color = Color.White)
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {

                OutlinedTextField(
                    value = messageInput.value,
                    singleLine = true,
                    onValueChange = { messageInput.value = it },
                    label = { Text("Enter text") },
                    modifier = Modifier.weight(1f).padding(8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    trailingIcon = {
                        IconButton(onClick = { sendMessage() }) {
                            Icon(Icons.Default.Send, "Send")
                        }
                    },
                    keyboardActions = KeyboardActions(onSend = {
                        sendMessage()
                        //softKeyboardController?.hideSoftwareKeyboard()
                    })
                )

                /*onImeActionPerformed = { action, softKeyboardController ->
                    if (action == ImeAction.Send) {
                        sendMessage()
                        softKeyboardController?.hideSoftwareKeyboard()
                    }
                }*/

                /*OutlinedTextField(
                    value = messageInput.value,
                    onValueChange = { messageInput.value = it },
                    modifier = Modifier.weight(1f).padding(8.dp),
                    textStyle = TextStyle(fontSize = 20.sp),
                    label = { Text("Message") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = { sendMessage() }) {
                            Icon(Icons.Default.Send, "Send")
                        }
                    }
                )*/
                Button(
                    onClick = { connectToChat() },
                    modifier = Modifier.padding(8.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.secondary)
                ) {
                    Text("Connect")
                }
            }
        }
    }
}




