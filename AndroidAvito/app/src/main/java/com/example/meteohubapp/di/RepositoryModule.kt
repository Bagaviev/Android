package com.example.meteohubapp.di

import com.example.meteohubapp.data.Repository
import com.example.meteohubapp.domain.IRepository
import dagger.Binds
import dagger.Module

/**
 * @author Bulat Bagaviev
 * @created 22.10.2022
 */
@Module
interface RepositoryModule {
    @Binds
    fun convertInterfaceToImpl(instance: Repository): IRepository
}
