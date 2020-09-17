package com.gvfiqst.tontestapp.data.util

import com.gvfiqst.tontestapp.domain.datatype.exception.HttpCode
import com.gvfiqst.tontestapp.domain.datatype.exception.HttpResponseException
import com.gvfiqst.tontestapp.domain.datatype.exception.NetworkConnectionException
import com.gvfiqst.tontestapp.domain.datatype.result.OpResult
import com.gvfiqst.tontestapp.domain.datatype.result.asErrorResult
import com.gvfiqst.tontestapp.domain.datatype.result.asSuccessResult
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException


inline fun <R> apiCall(block: () -> R): OpResult<R> {
    return try {
        return block().asSuccessResult()
    } catch (e: ConnectException) {
        NetworkConnectionException("Connection error")
            .asErrorResult()
    } catch (e: TimeoutException) {
        NetworkConnectionException("Timeout error")
            .asErrorResult()
    } catch (e: UnknownHostException) {
        NetworkConnectionException("Unknown server error")
            .asErrorResult()
    } catch (e: SSLException) {
        NetworkConnectionException("SSL error", e)
            .asErrorResult()
    } catch (e: HttpException) {
        HttpResponseException(HttpCode.valueOf(e.code()), e.message())
            .asErrorResult()
    } catch (e: Throwable) {
        NetworkConnectionException(e)
            .asErrorResult()
    }
}
