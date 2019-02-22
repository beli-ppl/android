package com.example.beli.service.step;

import com.example.beli.service.step.StepResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StepService {
    @GET("/step")
    Call<StepResponse> getAllStep();
}
