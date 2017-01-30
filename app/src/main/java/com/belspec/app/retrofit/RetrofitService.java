package com.belspec.app.retrofit;

import com.belspec.app.retrofit.model.createEvacuation.request.CreateEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.createEvacuation.response.CreateEvacuationResponseEnvelope;
import com.belspec.app.retrofit.model.createExtradition.request.CreateExtraditionRequestEnvelope;
import com.belspec.app.retrofit.model.createExtradition.response.CreateExtraditionResponseEnvelope;
import com.belspec.app.retrofit.model.createPoliceman.request.CreatePolicemanRequestEnvelope;
import com.belspec.app.retrofit.model.createPoliceman.response.CreatePolicemanResponseEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.request.GetCarOnEvacuationRequestEnvelope;
import com.belspec.app.retrofit.model.getCarOnEvacuation.response.GetCarOnEvacuationResponseEnvelope;
import com.belspec.app.retrofit.model.getDefaultData.request.GetDefaultDataRequestEnvelope;
import com.belspec.app.retrofit.model.getDefaultData.response.GetDefaultDataResponseEnvelope;
import com.belspec.app.retrofit.model.getPoliceDepartment.request.GetPoliceDepartmentRequestEnvelope;
import com.belspec.app.retrofit.model.getPoliceDepartment.response.GetPoliceDepartmentResponseEnvelope;
import com.belspec.app.retrofit.model.getPositions.request.GetPositionsRequestEnvelope;
import com.belspec.app.retrofit.model.getPositions.response.GetPositionsResponseEnvelope;
import com.belspec.app.retrofit.model.getRanks.request.GetRanksRequestEnvelope;
import com.belspec.app.retrofit.model.getRanks.response.GetRanksResponseEnvelope;
import com.belspec.app.retrofit.model.getRoadLawPoint.request.GetRoadLawPointRequestEnvelope;
import com.belspec.app.retrofit.model.getRoadLawPoint.response.GetRoadLawPointResponseEnvelope;
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

    @POST("ws/getdata")
    Call<GetRanksResponseEnvelope> executeGetRanks(@Header("Authorization") String auth, @Body GetRanksRequestEnvelope request);

    @POST("ws/getdata")
    Call<GetPositionsResponseEnvelope> executeGetPositions(@Header("Authorization") String auth, @Body GetPositionsRequestEnvelope request);

    @POST("ws/getdata")
    Call<GetPoliceDepartmentResponseEnvelope> executeGetPoliceDepartment(@Header("Authorization") String auth, @Body GetPoliceDepartmentRequestEnvelope request);

    @POST("ws/getdata")
    Call<GetRoadLawPointResponseEnvelope> executeGetRoadLawPoints(@Header("Authorization") String auth, @Body GetRoadLawPointRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/createdata")
    Call<CreateExtraditionResponseEnvelope> executeCreateExtradition(@Header("Authorization") String auth, @Body CreateExtraditionRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/createdata")
    Call<CreatePolicemanResponseEnvelope> executeCreatePoliceman(@Header("Authorization") String auth, @Body CreatePolicemanRequestEnvelope request);

}
