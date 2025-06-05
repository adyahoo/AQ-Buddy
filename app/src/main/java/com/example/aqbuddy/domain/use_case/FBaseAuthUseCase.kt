package com.example.aqbuddy.domain.use_case

import com.example.aqbuddy.data.local.MySharedPref
import com.example.aqbuddy.domain.model.User
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.example.aqbuddy.domain.repository.FBaseProfileRepository
import com.example.aqbuddy.ui.provider.session.SessionState
import com.example.aqbuddy.ui.provider.session.SessionStateHolder
import com.example.aqbuddy.utils.Constants
import com.example.aqbuddy.utils.Resource
import com.google.firebase.auth.FirebaseAuth
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

class FBaseBiometricLoginUseCase @Inject constructor(
    private val sharedPref: MySharedPref,
    private val sessionStateHolder: SessionStateHolder
) {
    operator fun invoke(): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading())

                sharedPref.setPref(Constants.USER_LOGIN_KEY, true)

                emit(
                    Resource.Success(
                        User(
                            name = "Biometric User",
                            email = "biometric@biometric.com",
                            photoUrl = "data:image/png;base64,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"
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

class FBaseLogoutUseCase @Inject constructor(
    private val authRepository: FBaseAuthRepository,
    private val sessionStateHolder: SessionStateHolder,
    private val sharedPref: MySharedPref,
) {
    operator fun invoke(): Flow<Resource<Void>> {
        return flow {
            try {
                emit(Resource.Loading())

                sharedPref.setPref(Constants.USER_LOGIN_KEY, false)
                sessionStateHolder.updateIsLoggedInState(SessionState.LoggedOut)

                authRepository.logout()

                emit(Resource.Success(null))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}

class GetUserUseCase @Inject constructor(
    private val auth: FirebaseAuth
) {
    operator fun invoke(): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading())

                val user = auth.currentUser

                emit(
                    Resource.Success(
                        User(
                            name = user?.displayName ?: "User",
                            email = user?.email ?: "user@biometric.com",
                            photoUrl = user?.photoUrl.toString()
                        )
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            }
        }
    }
}