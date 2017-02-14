package com.belspec.app.retrofit;

import android.util.Log;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class Api {

    private static final Charset UTF8 = Charset.forName("UTF-8");


    public static  RetrofitService createRetrofitService() {
        LoggingInterceptor interceptor = new LoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(3600, TimeUnit.SECONDS)
                .writeTimeout(3600, TimeUnit.SECONDS)
                .connectTimeout(3600,  TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(interceptor);
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl("")
                .client(httpClientBuilder.build())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return restAdapter.create(com.belspec.app.retrofit.RetrofitService.class);
    }

    public static class LoggingInterceptor implements Interceptor {

        private HttpLoggingInterceptor mInterceptor;

        public LoggingInterceptor() {
            mInterceptor = new HttpLoggingInterceptor();
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            return customIntercept(chain);
        }

        public void setLevel(HttpLoggingInterceptor.Level level) {
            mInterceptor.setLevel(level);
        }
    }

    public static Response customIntercept(Interceptor.Chain chain) throws IOException {

        RequestBody requestBody = chain.request().body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;
        String requestStartMessage = "--> " + chain.request().method() + ' ' + chain.request().url() + ' ' + protocol;
        Log.d("SOAP", requestStartMessage);


        long startNs = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        Log.d("SOAP", "<-- " + response.code() + ' ' + response.message() + ' '
                + response.request().url() + " (" + tookMs + "ms" + (!true ? ", "
                + bodySize + " body" : "") + ')');

        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();

        Charset charset = UTF8;
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            try {
                charset = contentType.charset(UTF8);
            } catch (UnsupportedCharsetException e) {

                return response;
            }
        }

        if (contentLength != 0) {
            Log.d("SOAP", "");
            String msg = buffer.clone().readString(charset);
            Log.d("SOAP", msg.replace("\\n", "").replace("\\r", "").replace("\\t", ""));
        }


        return response;
    }
}

