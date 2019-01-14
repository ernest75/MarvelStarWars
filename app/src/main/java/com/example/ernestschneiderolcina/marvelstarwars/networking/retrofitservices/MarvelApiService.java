package com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices;

import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.MarvelApi;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MarvelApiService {

    @GET("characters/")
    Observable<MarvelApi> getMarvelCharacters(@Query("ts")Long ts, @Query("apiKey")String apiKey, @Query("hash")String hash);


}
