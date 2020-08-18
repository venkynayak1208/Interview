package com.venky.interview

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.venky.interview.databinding.ActivitySplashBinding
import com.venky.interview.view.InterviewActivity


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySplashBinding = DataBindingUtil.setContentView(
                this, R.layout.activity_splash)
        binding.executePendingBindings();
        val handler = Handler()
        handler.postDelayed(Runnable {
            var a = Intent(this@SplashActivity, InterviewActivity::class.java)
                startActivity(a)
                finish()

        }, 1000)
    }
}