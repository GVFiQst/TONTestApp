package com.gvfiqst.tontestapp.domain.datatype.exception


sealed class NetworkException : AppException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}

class NoInternetException : NetworkException()

class NetworkConnectionException: NetworkException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}

class HttpResponseException(val code: HttpCode, message: String) : NetworkException("Response exception: $code $message")
