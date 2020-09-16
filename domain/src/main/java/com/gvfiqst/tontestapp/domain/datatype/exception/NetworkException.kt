package com.gvfiqst.tontestapp.domain.datatype.exception


sealed class NetworkException : AppException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}

class NoInternetException : NetworkException()

class HttpException(val code: HttpCode, message: String) : NetworkException(message)
