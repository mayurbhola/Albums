package com.example.albums.api

data class Result<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING_START
    }

    companion object {
        fun <T> success(data: T?): Result<T> {
            return Result(Status.SUCCESS, data)
        }

        fun <T> error(message: String): Result<T> {
            return Result(Status.ERROR, null, message)
        }

        fun <T> loadingStart(): Result<T> {
            return Result(Status.LOADING_START)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, message=$message"
    }
}

data class ErrorResponse(
    val status: Int? = 0,
    val message: String? = null
)
