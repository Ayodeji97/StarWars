package com.financials.starwars.business.datasource.remote.remotesource.utils

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.net.HttpURLConnection

//class Dispatcher : Dispatcher() {
//    override fun dispatch(request: RecordedRequest): MockResponse {
//        return when (request.path) {
//            "ss?search=$fkd" -> {
//                MockResponse()
//                    .setResponseCode(HttpURLConnection.HTTP_OK)
//                    .setBody(getJson())
//            }
//            else ->
//        }
//    }
//}