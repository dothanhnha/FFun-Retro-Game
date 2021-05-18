package com.nhado.ffunretrogame

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {
    private val DELAY_TIME = 500L

    suspend fun runTimeBackGround(updateUI: () -> Unit){
        withContext(Dispatchers.Default){
            while(true){
                delay(DELAY_TIME)
                withContext(Dispatchers.IO){
                    updateUI.invoke()
                }
            }
        }
    }
}