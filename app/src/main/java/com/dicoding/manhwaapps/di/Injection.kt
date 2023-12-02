package com.dicoding.manhwaapps.di

import com.dicoding.manhwaapps.data.ManhwaRepost

object Injection {
    fun provideRepository(): ManhwaRepost {
        return ManhwaRepost.getInstance()

    }
}