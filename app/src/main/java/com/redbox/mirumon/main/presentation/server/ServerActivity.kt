package com.redbox.mirumon.main.presentation.server

import android.app.Activity
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
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
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

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)
        sharedPref.getString(USER_LOGIN, null)?.let {
            loginEditText.text = SpannableStringBuilder(it)
        }
        sharedPref.getString(USER_SERVER, null)?.let {
            serverEditText.text = SpannableStringBuilder(it)
        }
//        if (sharedPref.getString(USER_SERVER, null) != null && sharedPref.getString(
//                USER_LOGIN,
//                null
//            ) != null
//        ) {
//            if (canAuthenticate(this)) {
//                val biometricPrompt: BiometricPrompt =
//                    BiometricPrompt.Builder(this)
//                        .setTitle("Mirumon Login")
//                        .setSubtitle("Sign in using fingerprint")
//                        .build()
//                biometricPrompt.authenticate()
//            }
//        }
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

//    fun isReachable(url: String) = GlobalScope.async(Dispatchers.IO) {
//        return@async InetAddress.getByName(url).isReachable(10000)
//    }

//    @RequiresApi(Build.VERSION_CODES.P)
//    fun createAuthenticationCallback(context: Context): BiometricPrompt.AuthenticationCallback {
//        return object : BiometricPrompt.AuthenticationCallback() {
//            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
//                super.onAuthenticationSucceeded(result)
//                Toast.makeText(context, "Authenticated!", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onAuthenticationFailed() {
//                super.onAuthenticationFailed()
//                Toast.makeText(context, "Authentication failed!", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }

//    fun canAuthenticate(context: Context): Boolean {
//        return BiometricManager.from(context)
//            .canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS
//    }
}