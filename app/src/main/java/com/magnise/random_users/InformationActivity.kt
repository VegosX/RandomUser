package com.magnise.random_users

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_information.*

class InformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information)

        val btnBack: Button = btn_back.findViewById(R.id.btn_back)
        val tvUserName: TextView = tvUsernameSpace.findViewById(R.id.tvUsernameSpace)
        val tvPhone: TextView = tvPhoneSpace.findViewById(R.id.tvPhoneSpace)
        val tvEmail: TextView = tvEmailSpace.findViewById(R.id.tvEmailSpace)
        val tvGender: TextView = tvGenderSpace.findViewById(R.id.tvGenderSpace)
        val tvAge: TextView = tvAgeSpace.findViewById(R.id.tvAgeSpace)
        val image: ImageView = ivIcon.findViewById(R.id.ivIcon)

        tvUserName.text = intent.getStringExtra("username")
        tvPhone.text = intent.getStringExtra("phone")
        tvEmail.text = intent.getStringExtra("email")
        tvGender.text = intent.getStringExtra("gender")
        tvAge.text = intent.getStringExtra("age")
        Glide.with(this).load(intent.getStringExtra("image")).into(image)

        btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}
