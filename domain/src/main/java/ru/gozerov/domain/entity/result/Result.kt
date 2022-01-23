package ru.gozerov.domain.entity.result

import java.io.Serializable

typealias Mapper<Input, Output> = (Input) -> Output

sealed class Result<T> : Serializable{

    fun <R> map(mapper: Mapper<T, R>? = null): Result<R> = when(this) {
        is PendingResult -> PendingResult()
        is ErrorResult -> ErrorResult(this.exception)
        is SuccessResult -> {
            if(mapper == null) throw IllegalArgumentException("Error (IAE)")
            SuccessResult(mapper(this.data))
        }
    }

}

fun <T> Result<T>.takeSuccess() :T? {
    return if (this is SuccessResult)
        this.data
    else
        null
}