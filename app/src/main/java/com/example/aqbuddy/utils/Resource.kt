package com.example.aqbuddy.utils

sealed class Resource<T>(val data: T? = null, val error: String? = null) {
    class Loading<T> : Resource<T>()
    class Success<T>(data: T?) : Resource<T>(data)
    class Error<T>(error: String?, data: T? = null) : Resource<T>(data, error)
}