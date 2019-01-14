package com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices;

import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.DogPictureApi;
import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.MarvelApi;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface DogApiService {

    @GET("images")
    Observable<DogPictureApi> getDogPictures();

    //https://dog.ceo/api/breed/hound/images
}
