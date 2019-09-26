package com.magnise.random_users

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        tvUsernameSpace.text = intent.getStringExtra("username")
        tvPhoneSpace.text = intent.getStringExtra("phone")
        tvEmailSpace.text = intent.getStringExtra("email")
        tvGenderSpace.text = intent.getStringExtra("gender")
        tvAgeSpace.text = intent.getStringExtra("age")
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
