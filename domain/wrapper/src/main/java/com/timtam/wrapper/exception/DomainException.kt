package com.timtam.wrapper.exception

import com.timtam.wrapper.type.ErrorDomainType

data class DomainException(
    val errorType: ErrorDomainType,
    override val message: String?
) : Exception()
