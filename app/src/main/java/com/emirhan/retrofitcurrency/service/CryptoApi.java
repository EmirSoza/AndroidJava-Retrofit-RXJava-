package com.emirhan.retrofitcurrency.service;

import com.emirhan.retrofitcurrency.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface CryptoApi {

    //URL BASE
    //@Url("https://api.nomics.com/v1")
    // GET
    @GET("prices?key=API_KEY")
    Observable<List<CryptoModel>> getData();

    //We used this without RxJava
   // Call<List<CryptoModel>> getData();
}
