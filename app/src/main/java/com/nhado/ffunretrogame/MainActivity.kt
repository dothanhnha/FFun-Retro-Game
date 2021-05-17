package com.nhado.ffunretrogame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_down.setOnClickListener {
            retroScreen.down()
        }
        btn_up.setOnClickListener {
            retroScreen.up()
        }
        btn_left.setOnClickListener {
            retroScreen.left()
        }
        btn_right.setOnClickListener {
            retroScreen.right()
        }
    }
}