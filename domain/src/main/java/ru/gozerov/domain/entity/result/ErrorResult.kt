package ru.gozerov.domain.entity.result

import java.io.Serializable

class ErrorResult<T>(
    val exception: Exception
) : FinalResult<T>(), Serializable