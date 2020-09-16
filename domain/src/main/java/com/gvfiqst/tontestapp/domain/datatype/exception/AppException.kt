package com.gvfiqst.tontestapp.domain.datatype.exception

import java.lang.RuntimeException


abstract class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
}