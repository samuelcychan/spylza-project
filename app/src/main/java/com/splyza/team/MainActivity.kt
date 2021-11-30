package com.splyza.team

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @Suppress("UNUSED_PARAMETER")
    fun invite(view: android.view.View) {
        val teamId = "57994f271ca5dd20847b910c"
        Intent(this, InviteActivity::class.java).apply {
            putExtra("team_id", teamId)
            startActivity(this)
        }
    }
}