package com.dicoding.manhwaapps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.manhwaapps.data.ManhwaRepost

class VMFactory(private val repository: ManhwaRepost) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVM::class.java)) {
            return HomeVM(repository) as T
        } else if (modelClass.isAssignableFrom(DetailVM::class.java)) {
            return DetailVM(repository) as T
        } else if (modelClass.isAssignableFrom(FavoriteVM::class.java)) {
            return FavoriteVM(repository) as T
        }
        throw IllegalArgumentException("Unknown VM class: " + modelClass.name)
    }
}