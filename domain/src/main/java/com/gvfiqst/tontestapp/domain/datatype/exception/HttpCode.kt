package com.gvfiqst.tontestapp.domain.datatype.exception


enum class HttpCode(val code: Int) {
    OK(200),
    Created(201),
    BadRequest(400),
    Unauthorized(401),
    Forbidden(403),
    NotFound(404),
    DeprecatedAppVersion(406),
    UnProcessableEntity(422),
    InternalServerError(500),
    Unknown(-1);

    companion object {
        fun valueOf(code: Int): HttpCode {
            return values().firstOrNull { it.code == code } ?: Unknown
        }
    }

}
