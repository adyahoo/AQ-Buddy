package com.example.aqbuddy.domain.use_case

import com.example.aqbuddy.domain.model.User
import com.example.aqbuddy.domain.repository.FBaseAuthRepository
import com.example.aqbuddy.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FBaseRegisterUseCase @Inject constructor(
    private val authRepository: FBaseAuthRepository
) {
    operator fun invoke(email: String, password: String): Flow<Resource<User>> {
        return flow {
            try {
                emit(Resource.Loading())

                val res = authRepository.register(email, password)

                emit(
                    Resource.Success(
                        User(
                            name = res?.user?.displayName ?: "",
                            email = res?.user?.email ?: "",
                            photoUrl = res?.user?.photoUrl.toString()
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