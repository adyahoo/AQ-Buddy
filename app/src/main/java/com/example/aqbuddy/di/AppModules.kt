package com.example.aqbuddy.di

import com.example.aqbuddy.data.repository.FBaseAuthRepositoryImpl
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFBaseAuthRepository(firebaseAuth: FirebaseAuth): FBaseAuthRepository = FBaseAuthRepositoryImpl(firebaseAuth)
}