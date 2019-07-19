package com.bounce.network;

import com.bounce.model.Request;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkService {

    @POST
    Single<Boolean> postRequestData(@Body Request request);

}
