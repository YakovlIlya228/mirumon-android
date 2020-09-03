package com.redbox.mirumon.main.presentation.server

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.redbox.mirumon.BuildConfig.USER_SERVER
import com.redbox.mirumon.BuildConfig.USER_TOKEN
import com.redbox.mirumon.R
import com.redbox.mirumon.main.domain.pojo.LoginUser
import com.redbox.mirumon.main.domain.pojo.Token
import com.redbox.mirumon.main.presentation.main.MainActivity
import kotlinx.android.synthetic.main.activity_server.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.scope.lifecycleScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.URL

class ServerActivity : AppCompatActivity() {

    val viewModel: ServerViewModel by viewModel(named("noAuthViewModel"))
    val sharedPref: SharedPreferences by inject()
    val TAG = "error_tag"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        val sharedPref =
            getSharedPreferences(getString(R.string.pref_file_key), Context.MODE_PRIVATE)
        serverButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val token: Token = viewModel.loginUser(
                        loginEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                    sharedPref.edit().putString(USER_TOKEN, token.accessToken).apply()
                    sharedPref.edit().putString(USER_SERVER, serverEditText.text.toString())
                        .apply()
                    val intent = Intent(this@ServerActivity, MainActivity::class.java)
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(TAG, "Couldn't get token!")
                }
            }
        }
    }
}