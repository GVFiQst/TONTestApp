package com.gvfiqst.tontestapp.domain.datatype.exception


sealed class FeatureException : AppException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}

class SearchException(val reason: Reason, message: String) : FeatureException("reason: $reason, message: $message") {

    enum class Reason {
        TooManyResults, MovieNotFound, Unknown
    }

}
