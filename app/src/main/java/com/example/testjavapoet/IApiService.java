/*
 * Copyright 2015-2018 Hive Box.
 */

package com.example.testjavapoet;

import com.example.annotation.Inter;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Locker API service.
 *
 * @author xuzhuyun
 */
@SuppressWarnings("unused")
public interface IApiService {

    /**
     * 取件失败结果上报.
     */
    @POST("dropoff/explain")
    @Inter
    Flowable<Response<ExplainResp>> getExplainReq(@Body Request<ExplainReq> request);
    /**
     * 取件失败结果上报.
     */
    @POST("dropoff/explain")
    @Inter
    Flowable<Response<LawsuitResp>> getReq(@Body Request<LawsuitReq> request);

//    @POST("dropoff/explain")
//    @Inter
//    Flowable<Response<ExplainResp>> getReq(@Body Request<ExplainReq> request);
//
//    @POST("dropoff/explain")
//    @Inter
//    Flowable<Response<LawsuitResp>> getLawsuit(@Body Request<LawsuitReq> request);


}
