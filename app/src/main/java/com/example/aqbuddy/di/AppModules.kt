package com.example.aqbuddy.di

import com.example.aqbuddy.data.remote.api.RouteServiceApi
import com.example.aqbuddy.data.repository.FBaseAuthRepositoryImpl
import com.example.aqbuddy.data.repository.FBaseMapRepositoryImpl
import com.example.aqbuddy.data.repository.FBaseProfileRepositoryImpl
import com.example.aqbuddy.data.repository.RouteServiceRepositoryImpl
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.example.aqbuddy.domain.repository.FBaseMapRepository
import com.example.aqbuddy.domain.repository.FBaseProfileRepository
import com.example.aqbuddy.domain.repository.RouteServiceRepository
import com.example.aqbuddy.ui.provider.session.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Route
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModules {
    @Provides
    @Singleton
    fun provideRouteServiceApi(): RouteServiceApi = Retrofit.Builder()
        .baseUrl(Constants.OPEN_ROUTE_SERVICE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RouteServiceApi::class.java)

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideSessionStateHolder(): SessionStateHolder = SessionStateHolder()

    // Repositories
    @Provides
    @Singleton
    fun provideRouteServiceRepository(api: RouteServiceApi): RouteServiceRepository {
        return RouteServiceRepositoryImpl(api)
    }

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