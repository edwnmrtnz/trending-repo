package com.edwnmrtnz.trendingrepo.core.domain.exceptions

class TrendyServiceFailureException(
    message: String,
    val exception: Exception
) : TrendyException(message)
