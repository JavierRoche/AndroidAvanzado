package com.example.cocktailclub.common

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailclub.ui.detail.DetailViewModel
import com.example.cocktailclub.ui.main.MainViewModel
import java.lang.IllegalArgumentException

/**
 * VIEWMODELPRIVIDER DEPRECATED PROBLEM
 */

@Suppress("UNCHECKED_CAST")
class CustomViewModel(private val application: Application): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) -> MainViewModel(application)
                isAssignableFrom(DetailViewModel::class.java) -> DetailViewModel(application)
                else -> throw IllegalArgumentException("Unkown ViewModel")
            }
        } as T
    }
}