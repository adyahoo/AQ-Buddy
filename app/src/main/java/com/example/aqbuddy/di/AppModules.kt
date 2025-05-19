package com.example.aqbuddy.di

import com.example.aqbuddy.data.repository.FBaseAuthRepositoryImpl
import com.example.aqbuddy.data.repository.FBaseMapRepositoryImpl
import com.example.aqbuddy.data.repository.FBaseProfileRepositoryImpl
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.example.aqbuddy.domain.repository.FBaseMapRepository
import com.example.aqbuddy.domain.repository.FBaseProfileRepository
import com.example.aqbuddy.ui.provider.session_provider.SessionStateHolder
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
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
    fun provideSessionStateHolder(): SessionStateHolder = SessionStateHolder()

    // Repositories
    @Provides
    @Singleton
    fun provideFBaseAuthRepository(firebaseAuth: FirebaseAuth): FBaseAuthRepository {
        return FBaseAuthRepositoryImpl(firebaseAuth)
    }
    @Provides
    @Singleton
    fun provideFBaseProfileRepository(firebaseAuth: FirebaseAuth): FBaseProfileRepository {
        return FBaseProfileRepositoryImpl(firebaseAuth)
    }
    @Provides
    @Singleton
    fun provideFBaseMapRepository(): FBaseMapRepository {
        return FBaseMapRepositoryImpl()
    }
}