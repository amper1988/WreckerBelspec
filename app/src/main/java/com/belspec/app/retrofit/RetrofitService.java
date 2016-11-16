package com.belspec.app.retrofit;

import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseEnvelope;
import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestEnvelope;
import com.belspec.app.retrofit.model.createExtradition.response.CreateExtraditionResponseEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.request.GetCarOnEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.GetCarOnEvacuationResponseEnvelope;
import com.belspec.app.retrofit.model.getDefaultData.request.GetDefaultDataRequestEnvelope;
import com.belspec.app.retrofit.model.getDefaultData.response.GetDefaultDataResponseEnvelope;
import com.belspec.app.retrofit.model.test.request.TestRequestEnvelope;
import com.belspec.app.retrofit.model.test.response.TestResponseEnvelope;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitService {
    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/testconnection")
    Call<TestResponseEnvelope> executeTestOperation(@Header("Authorization") String auth, @Body TestRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/getdata")
    Call<GetDefaultDataResponseEnvelope> executeGetDefaultData(@Header("Authorization") String auth, @Body GetDefaultDataRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/createdata")
    Call<CreateEvacuationResponseEnvelope> executeCreateEvacuation(@Header("Authorization") String auth, @Body CreateEvacuationRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/getdata")
    Call<GetCarOnEvacuationResponseEnvelope> executeGetCarOnEvacuation(@Header("Authorization") String auth, @Body GetCarOnEvacuationRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/createdata")
    Call<CreateExtraditionResponseEnvelope> executeCreateExtradition(@Header("Authorization") String auth, @Body CreateExtraditionRequestEnvelope request);

}
