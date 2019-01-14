package com.example.ernestschneiderolcina.marvelstarwars.dagger;

import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.DogApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.MarvelApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ernestschneiderolcina.marvelstarwars.constants.Constants.BASE_URL_DOG_PICTURES_API;
import static com.example.ernestschneiderolcina.marvelstarwars.constants.Constants.BASE_URL_MARVEL;
import static com.example.ernestschneiderolcina.marvelstarwars.constants.Constants.BASE_URL_STAR_WARS;

@Module
public class NetworkingModule {


    @Provides
    Retrofit getRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    @Provides
    StarWarsApiService getStarWarsApi(){
        return getRetrofit(BASE_URL_STAR_WARS).create(StarWarsApiService.class);
    }
    @Provides
    MarvelApiService getMarvelApi(){
        return getRetrofit(BASE_URL_MARVEL).create(MarvelApiService.class);
    }

    @Provides
    DogApiService getDogApiService(){
        return getRetrofit(BASE_URL_DOG_PICTURES_API).create(DogApiService.class);
    }









}
