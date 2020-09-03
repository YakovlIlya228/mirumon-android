package com.redbox.mirumon.main.presentation.server

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.LoginUser
import com.redbox.mirumon.main.domain.pojo.Token
import com.redbox.mirumon.main.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.coroutines.*
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

class ServerActivity : AppCompatActivity() {

    val viewModel: ServerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        val sharedPref =
            getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE)
        serverButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
                if (InetAddress.getByName(serverEditText.text.toString()).isReachable(10000)) {
                    try {
                        val token: Token = viewModel.loginUser(
                            LoginUser(
                                loginEditText.text.toString(),
                                passwordEditText.text.toString()
                            )
                        )
                        sharedPref.edit().putString("USER_TOKEN", token.accessToken).apply()
                        sharedPref.edit().putString("TOKEN_TYPE", token.accessToken).apply()
                        sharedPref.edit().putString("USER_SERVER", serverEditText.text.toString())
                            .apply()
                        val goToMain = Intent(this@ServerActivity, MainActivity::class.java)
                        startActivity(goToMain)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                }
            }
            Toast.makeText(this, "Specified server is not available!", Toast.LENGTH_SHORT)
                .show()
        }
    }
}