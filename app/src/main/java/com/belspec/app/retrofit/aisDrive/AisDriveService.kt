package com.belspec.app.retrofit.aisDrive

import com.belspec.app.retrofit.Api
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface AisDriveService {

    @POST("movable/external/call")
    fun createRequest(@Body params: JsonObject): Call<AisResponse>

    @GET("movable/external/call/{id}")
    fun getRequest(@Path("id") id: Long): Call<AisResponse>

    @DELETE("movable/external/call/{id}")
    fun deleteRequest(@Path("id") id: Long): Call<Void>

    companion object {
        private fun create() : AisDriveService{
            return Api.createAisDriveService()
        }

        fun createRequest(gps: AisGps, carNumber: String, address: String, phone: String, categoriesGoods: String = "35"): Call<AisResponse>{
            val jsonObject = JsonObject()
            jsonObject.add("gps", Gson().toJsonTree(gps))
            jsonObject.addProperty("carNumber", carNumber)
            jsonObject.addProperty("address", address)
            jsonObject.addProperty("phone", phone)
            jsonObject.addProperty("categoriesGoods", categoriesGoods)
            return create().createRequest(jsonObject)
        }

        fun getRequest(id: Long): Call<AisResponse>{
            return create().getRequest(id)
        }

        fun deleteRequest(id: Long): Call<Void>{
            return create().deleteRequest(id)
        }
    }
}