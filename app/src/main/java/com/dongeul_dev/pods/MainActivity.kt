package com.dongeul_dev.pods

import android.app.NotificationManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start.setOnClickListener {
            PodService.startService(this@MainActivity, "포그라운드 서비스 시작")
        }

        stop.setOnClickListener {
            PodService.stopService(this@MainActivity)
        }

        notify.setOnClickListener {
            PodService.notifyYo(this@MainActivity)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
