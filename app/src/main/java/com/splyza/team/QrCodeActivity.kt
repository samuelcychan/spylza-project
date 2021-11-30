package com.splyza.team

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.sumimakito.awesomeqr.AwesomeQrRenderer
import com.github.sumimakito.awesomeqr.RenderResult
import com.github.sumimakito.awesomeqr.option.RenderOption
import com.splyza.team.databinding.ActivityQrCodeBinding

class QrCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityQrCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val renderOption = RenderOption()
        renderOption.content = intent.getStringExtra("link")!!
        renderOption.size = 800
        renderOption.borderWidth = 20

        try {
            val result = AwesomeQrRenderer.render(renderOption)
            when {
                result.bitmap != null || result.type == RenderResult.OutputType.GIF -> {
                    binding.qrcodeImage.setImageBitmap(result.bitmap)
                }
                else -> handleQrCodeFailure()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            handleQrCodeFailure()
        }
    }

    private fun handleQrCodeFailure() {
        Toast.makeText(this, getString(R.string.qrcode_failure), Toast.LENGTH_LONG).show()
        finish()
    }
}