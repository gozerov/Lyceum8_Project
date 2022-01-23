package ru.gozerov.domain.entity.result

import java.io.Serializable

class SuccessResult<T>(
    val data: T,
) : FinalResult<T>(), Serializable