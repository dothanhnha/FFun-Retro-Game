package com.nhado.ffunretrogame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_down.setOnClickListener {
            retroScreen.down()
        }
        btn_left.setOnClickListener {
            retroScreen.left()
        }
        btn_right.setOnClickListener {
            retroScreen.right()
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.runTimeBackGround {
                retroScreen.down()
            }
        }
    }
}