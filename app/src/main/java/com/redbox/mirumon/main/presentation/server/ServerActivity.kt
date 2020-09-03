package com.redbox.mirumon.main.presentation.server

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.redbox.mirumon.BuildConfig.*
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
        sharedPref.getString(USER_LOGIN, null)?.let {
            loginEditText.text = SpannableStringBuilder(it)
        }
        sharedPref.getString(USER_SERVER, null)?.let {
            serverEditText.text = SpannableStringBuilder(it)
        }
        serverButton.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                try {
                    val token: Token = viewModel.loginUser(
                        loginEditText.text.toString(),
                        passwordEditText.text.toString()
                    )
                    sharedPref.edit().putString(USER_TOKEN, token.accessToken).apply()
                    sharedPref.edit().putString(USER_LOGIN, loginEditText.text.toString()).apply()
                    sharedPref.edit().putString(USER_SERVER, serverEditText.text.toString())
                        .apply()
                    val intent = Intent(this@ServerActivity, MainActivity::class.java)
                    startActivity(intent)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        applicationContext,
                        "Username or/and password invalid!",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "Couldn't get token!")
                }
            }
        }
    }

    fun isReachable(url: String) = GlobalScope.async(Dispatchers.IO) {
        return@async InetAddress.getByName(url).isReachable(10000)
    }
}


//    @RequiresApi(Build.VERSION_CODES.P)
//    fun createBiometricPrompt(): {
//        val executor = ContextCompat.getMainExecutor(this)
//        val callback = @RequiresApi(Build.VERSION_CODES.P)
//        object: BiometricPrompt.AuthenticationCallback() {
//            override fun equals(other: Any?): Boolean {
//                return super.equals(other)
//            }
//
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
//                super.onAuthenticationSucceeded(result)
//                val intent = Intent(this@ServerActivity, MainActivity::class.java)
//                startActivity(intent)
//
//            }
//        }
//
//    }

