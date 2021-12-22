package com.ke.plate_id_recngnition

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kernal.plateid.PlateRecognitionResult
import com.kernal.plateid.RxPlateRecognition

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.plate_number)
        val imageView = findViewById<ImageView>(R.id.imageView)


        findViewById<Button>(R.id.start).setOnClickListener { _ ->
            //need a valid key (QMVPAMLUZYBFCGF)
            RxPlateRecognition(this).start("QMVPAMLUZYBFCGF")
                .subscribe {
                    when (it) {
                        is PlateRecognitionResult.Success -> {
                            text.text = it.plate
                            imageView.setImageBitmap(base64ToBitmap(it.base64Data))
                        }
                        is PlateRecognitionResult.Error -> {
                            AlertDialog.Builder(this)
                                .setTitle("识别出错")
                                .setMessage(it.message)
                                .show()
                        }
                    }
                }
        }


    }

    private fun base64ToBitmap(base64String: String): Bitmap? {
        val decode = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decode, 0, decode.size)
    }

}
