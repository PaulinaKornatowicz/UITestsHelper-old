package com.paulinakornatowicz.uitestshelperdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {

    private lateinit var appIdleStateReceiver: AppIdleStateReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appIdleStateReceiver =
            AppIdleStateReceiver()
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        appIdleStateReceiver.onBusyOperationStarted()
        Handler().postDelayed({
            appIdleStateReceiver.onBusyOperationEnded()
        }, 15000L)
    }
}
