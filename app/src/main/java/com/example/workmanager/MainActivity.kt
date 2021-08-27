package com.example.workmanager

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.workmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notifyBtn: Button
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notifyBtn = binding.notifyBtn
        statusTextView = binding.statusTv

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        // Send Notification at the click of the button
        notifyBtn.setOnClickListener {
            WorkManager.getInstance(applicationContext).enqueue(oneTimeWorkRequest)
        }

        // Get the status of the work in the text view
        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(
                this,
                {
                    statusTextView.text =
                        it.state.name
                }
            )
    }
}
