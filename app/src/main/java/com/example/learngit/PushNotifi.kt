package com.example.learngit

import android.app.VoiceInteractor
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

const val ONESIGNAL_APP_ID = ""
const val REST_API_KEY = ""

class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push_notifi)

        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)

        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(false)
        }

        val button: Button = findViewById(R.id.buttonSS)
        button.setOnClickListener {
            sendNotification("Butona Tıklandı", "Butona tıklandı bildirimi")
        }
    }

    private fun sendNotification(title: String, message: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val json = """
                    {
                        "app_id": "$ONESIGNAL_APP_ID",
                        "included_segments": ["All"],
                        "headings": {"en": "$title"},
                        "contents": {"en": "$message"}
                    }
                """.trimIndent()

            val body: RequestBody =
                json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request: VoiceInteractor.Request = VoiceInteractor.Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .post(body)
                .addHeader("Authorization", "Basic $REST_API_KEY")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .build()

            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

            }
        }
    }
}