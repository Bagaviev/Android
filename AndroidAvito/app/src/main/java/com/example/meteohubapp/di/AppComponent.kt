package com.example.meteohubapp.di

import com.example.meteohubapp.data.network.NetworkModule
import com.example.meteohubapp.domain.IRepository
import dagger.Component

/**
 * @author Bulat Bagaviev
 * @created 19.11.2021
 */

@Component(modules = [NetworkModule::class, RepositoryModule::class])
interface AppComponent {
    fun getRepository(): IRepository
}