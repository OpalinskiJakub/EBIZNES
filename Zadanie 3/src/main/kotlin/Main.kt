import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import org.slf4j.LoggerFactory

@Serializable
data class DiscordWebhook(val content: String)

fun main() {
    val logger = LoggerFactory.getLogger("DiscordClient")


    val webhookUrl = "https://discord.com/api/webhooks/x/x"
    val message = "TEST KOTLIN !!!"

    runBlocking {
        try {
            val client = HttpClient(CIO) {
                install(ContentNegotiation) {
                    json()
                }
            }

            val response = client.post(webhookUrl) {
                contentType(ContentType.Application.Json)
                setBody(DiscordWebhook(message))
            }

            if (response.status.isSuccess()) {
                logger.info("Wiadomość została wysłana na Discord!")
            } else {
                logger.error("Wystąpił błąd podczas wysyłania wiadomości: ${response.status}")
                logger.error("Treść odpowiedzi: ${response.body<String>()}")
            }

            client.close()
        } catch (e: Exception) {
            logger.error("Wystąpił nieoczekiwany błąd: ${e.message}")
        }
    }
}