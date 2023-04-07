
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.*
import java.time.*

fun main() {
    val server = embeddedServer(Netty, port = 8080) {
        install(WebSockets)
        routing {
            println("Server routing is called")
            webSocket("/chat") {
                val user = User(this)
                user.addToChat()
                println("Server User session is created")
                try {
                    for (frame in incoming) {
                        frame as? Frame.Text ?: continue
                        val receivedText = frame.readText()
                        println("Server:: received msg $receivedText ")
                        val message = "${user.name}: $receivedText"
                        ChatServer.sendToAll(message)
                    }
                } finally {
                    user.removeFromChat()
                }
            }
        }
    }
    println("Server initialization done")
    server.start(wait = true)
    println("Server Started done")
}

object ChatServer {
    private val users = mutableListOf<User>()
    private val messageHistory = mutableListOf<String>()

    suspend fun sendToAll(message: String) {
        val timestamp = LocalTime.now()
        messageHistory.add("[$timestamp] $message")
        for (user in users) {
            user.send(message)
        }
    }

    suspend fun addUser(user: User) {
        users.add(user)
        for (message in messageHistory) {
            user.send(message)
        }
    }

    fun removeUser(user: User) {
        users.remove(user)
    }
}

class User(private val session: WebSocketSession) {
    val name = "user${(Math.random() * 100).toInt()}"
    private val outgoing = Channel<String>()

    suspend fun send(message: String) {
        outgoing.send(message)
    }

    suspend fun addToChat() {
        ChatServer.addUser(this)
        for (message in outgoing) {
            session.send(message)
        }
    }

    fun removeFromChat() {
        ChatServer.removeUser(this)
    }
}
