package com.edwnmrtnz.trendingrepo.core.domain.exceptions

import java.lang.Exception

class TrendyHttpErrorException(
    val code: Int,
    message: String,
    val exception: Exception
) : TrendyException(message)
