package com.bounce.network;

import com.bounce.model.DataResponse;
import com.bounce.model.Request;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NetworkService {

    @POST("simulate/{noOfRequests}")
    Single<Void> postRequestData(@Path("noOfRequests") int noOfRequests);

}
