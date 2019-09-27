package com.magnise.random_users

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_information.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class InformationActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val parsedDate = LocalDateTime.parse(intent.getStringExtra("date"), DateTimeFormatter.ISO_DATE_TIME)
        val formattedDate = parsedDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

        tvUsernameSpace.text = intent.getStringExtra("username")
        tvPhoneSpace.text = intent.getStringExtra("phone")
        tvEmailSpace.text = intent.getStringExtra("email")
        tvGenderSpace.text = intent.getStringExtra("gender")
        tvAgeSpace.text = intent.getStringExtra("age")
        tvDateSpace.text = formattedDate
        Glide.with(this).load(intent.getStringExtra("image")).into(ivIcon)

        btn_back.setOnClickListener {
            onBackPressed()
        }

        tvPhoneSpace.setOnClickListener { startActivity(
            Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${intent.getStringExtra("phone")}")
            ))
        }
    }
}
