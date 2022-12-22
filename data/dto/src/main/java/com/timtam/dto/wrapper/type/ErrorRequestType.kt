package com.timtam.dto.wrapper.type

enum class ErrorRequestType(val errorCode: Int) {
    NO_CONNECTION(1000),
    TIMEOUT(1001),
    CLIENT_ERROR(1002),
    SERVER_ERROR(1003),
    UNKNOWN_ERROR(1004)
}
