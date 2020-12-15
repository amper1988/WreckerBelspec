package com.belspec.app.retrofit

import okhttp3.OkHttpClient
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

fun OkHttpClient.Builder.enableTrustAll() = apply {
    try {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, arrayOf<TrustManager>(trustAllManager), null)
        val sslSocketFactory = sslContext.socketFactory

        sslSocketFactory(sslSocketFactory, trustAllManager as X509TrustManager)
        hostnameVerifier { _, _ -> true }

    } catch (e: Exception) {
        throw RuntimeException(e)
    }
}

private val trustAllManager by lazy {
    object : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {}
        override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
    }
}