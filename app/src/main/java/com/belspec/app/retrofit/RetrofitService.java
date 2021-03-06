package com.belspec.app.retrofit;

import com.belspec.app.retrofit.model.checkUpdate.request.CheckUpdateRequestEnvelope;
import com.belspec.app.retrofit.model.checkUpdate.response.CheckUpdateResponseEnvelope;
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
import com.belspec.app.retrofit.model.getDriverData.request.GetDriverDataRequestEnvelope;
import com.belspec.app.retrofit.model.getDriverData.response.GetDriverDataResponseEnvelope;
import com.belspec.app.retrofit.model.getOwnerData.request.GetOwnerDataRequestEnvelope;
import com.belspec.app.retrofit.model.getOwnerData.response.GetOwnerDataResponseEnvelope;
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
    @POST("ws/MobileAppWebServices")
    Call<TestResponseEnvelope> executeTestOperation(@Header("Authorization") String auth, @Body TestRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<GetDefaultDataResponseEnvelope> executeGetDefaultData(@Header("Authorization") String auth, @Body GetDefaultDataRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<CreateEvacuationResponseEnvelope> executeCreateEvacuation(@Header("Authorization") String auth, @Body CreateEvacuationRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<GetCarOnEvacuationResponseEnvelope> executeGetCarOnEvacuation(@Header("Authorization") String auth, @Body GetCarOnEvacuationRequestEnvelope request);

    @POST("ws/MobileAppWebServices")
    Call<GetRanksResponseEnvelope> executeGetRanks(@Header("Authorization") String auth, @Body GetRanksRequestEnvelope request);

    @POST("ws/MobileAppWebServices")
    Call<GetPositionsResponseEnvelope> executeGetPositions(@Header("Authorization") String auth, @Body GetPositionsRequestEnvelope request);

    @POST("ws/MobileAppWebServices")
    Call<GetPoliceDepartmentResponseEnvelope> executeGetPoliceDepartment(@Header("Authorization") String auth, @Body GetPoliceDepartmentRequestEnvelope request);

    @POST("ws/MobileAppWebServices")
    Call<GetRoadLawPointResponseEnvelope> executeGetRoadLawPoints(@Header("Authorization") String auth, @Body GetRoadLawPointRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<CreateExtraditionResponseEnvelope> executeCreateExtradition(@Header("Authorization") String auth, @Body CreateExtraditionRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<CreatePolicemanResponseEnvelope> executeCreatePoliceman(@Header("Authorization") String auth, @Body CreatePolicemanRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<CheckUpdateResponseEnvelope> executeCheckUpdate(@Header("Authorization") String auth, @Body CheckUpdateRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<GetOwnerDataResponseEnvelope> executeGetOwnerData(@Header("Authorization") String auth, @Body GetOwnerDataRequestEnvelope request);

    @Headers({
            "Content-Type: application/soap+xml",
            "Accept-Charset: utf-8"
    })
    @POST("ws/MobileAppWebServices")
    Call<GetDriverDataResponseEnvelope> executeGetDriverData(@Header("Authorization") String auth, @Body GetDriverDataRequestEnvelope request);



}
