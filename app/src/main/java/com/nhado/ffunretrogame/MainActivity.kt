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
            retroScreen.move(Brick.Movement.DOWN)
        }
        btn_left.setOnClickListener {
            retroScreen.move(Brick.Movement.LEFT)
        }
        btn_right.setOnClickListener {
            retroScreen.move(Brick.Movement.RIGHT)
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            viewModel.runTimeBackGround {
                retroScreen.move(Brick.Movement.DOWN)
            }
        }
    }
}