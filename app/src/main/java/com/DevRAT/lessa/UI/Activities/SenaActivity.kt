package com.DevRAT.lessa.UI.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.DevRAT.lessa.Database.Entities.Senas
import com.DevRAT.lessa.R
import kotlinx.android.synthetic.main.activity_sena.*

class SenaActivity : AppCompatActivity() {
    companion object{
        var sena : Senas? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sena).apply {
            textView2.text = sena?.palabra
        }

    }
}