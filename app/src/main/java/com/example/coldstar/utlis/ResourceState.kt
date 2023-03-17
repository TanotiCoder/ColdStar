package com.example.coldstar.utlis

sealed class ResourceState<T>(val data: T? = null, val statusMessage: String? = null) {
    class Success<T>(data: T) : ResourceState<T>(data = data)
    class Loading<T>(data: T? = null) : ResourceState<T>(data = data)
    class Error<T>(message: String, data: T? = null) : ResourceState<T>(statusMessage = message)
}