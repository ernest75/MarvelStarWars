package com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices;

import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.StarWarsApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StarWarsApiService {

    @GET("people/")
    Observable<StarWarsApi> getStarWarsCharacters(@Query("format") String format);

    //    format=json&search=a
// https://swapi.co/api/people/?format=json&search=a
//

}
