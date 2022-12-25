package com.timtam.dto.model.base

interface PeekableDTO {

    fun isEmpty(): Boolean

    fun isNotEmpty(): Boolean = !isEmpty()
}
