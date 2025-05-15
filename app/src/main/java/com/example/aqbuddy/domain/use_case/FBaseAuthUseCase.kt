package com.example.aqbuddy.domain.use_case

import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.domain.model.User
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.example.aqbuddy.domain.repository.FBaseProfileRepository
import com.example.aqbuddy.ui.SessionState
import com.example.aqbuddy.ui.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import com.example.aqbuddy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FBaseRegisterUseCase @Inject constructor(
    private val authRepository: FBaseAuthRepository,
    private val profileRepository: FBaseProfileRepository,
    private val sharedPref: MySharedPref,
    private val sessionStateHolder: SessionStateHolder
) {
    operator fun invoke(name: String, email: String, password: String): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading())

                val res = authRepository.register(email, password)

                val user = profileRepository.updateProfile(
                    name = name,
                    user = res?.user
                )

                sharedPref.setPref(Constants.USER_LOGIN_KEY, true)

                emit(
                    Resource.Success(
                        User(
                            name = user.displayName ?: "",
                            email = user.email ?: "",
                            photoUrl = user.photoUrl.toString()
                        )
                    )
                )

                sessionStateHolder.updateIsLoggedInState(SessionState.LoggedIn)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}

class FBaseLoginUseCase @Inject constructor(
    private val authRepository: FBaseAuthRepository,
    private val sharedPref: MySharedPref,
    private val sessionStateHolder: SessionStateHolder
) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading())

                val res = authRepository.login(email, password)
                sharedPref.setPref(Constants.USER_LOGIN_KEY, true)

                emit(
                    Resource.Success(
                        User(
                            name = res?.user?.displayName ?: "",
                            email = res?.user?.email ?: "",
                            photoUrl = res?.user?.photoUrl.toString()
                        )
                    )
                )

                sessionStateHolder.updateIsLoggedInState(SessionState.LoggedIn)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}
