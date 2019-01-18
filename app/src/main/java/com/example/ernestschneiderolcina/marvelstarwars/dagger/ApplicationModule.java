package com.example.ernestschneiderolcina.marvelstarwars.dagger;

import android.app.Application;
import android.content.Context;

import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.DogApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.MarvelApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;
import com.example.ernestschneiderolcina.marvelstarwars.screens.detail.DetailMVP;
import com.example.ernestschneiderolcina.marvelstarwars.screens.detail.DetailModel;
import com.example.ernestschneiderolcina.marvelstarwars.screens.detail.DetailPresenter;
import com.example.ernestschneiderolcina.marvelstarwars.screens.main.MainActivityModel;
import com.example.ernestschneiderolcina.marvelstarwars.screens.main.MainActivityPresenter;
import com.example.ernestschneiderolcina.marvelstarwars.screens.main.MainMVP;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application){
        mApplication = application;
    }

    @Provides
    @Singleton
    Context provideContext(){
        return mApplication;
    }

    @Provides
    MainMVP.Model provideModel(StarWarsApiService starWarsApiService, MarvelApiService marvelApiService, DogApiService dogApiService){
        return new MainActivityModel(starWarsApiService,marvelApiService, dogApiService);
    }

    @Provides
    MainMVP.Presenter provideMainPresenter(StarWarsApiService starWarsApiService, MarvelApiService marvelApiService, DogApiService dogApiService){
        return new MainActivityPresenter(provideContext(),provideModel(starWarsApiService,marvelApiService,dogApiService));
    }

    @Provides
    DetailMVP.Model provideDetailModel(){
        return new DetailModel();
    }

    @Provides
    DetailMVP.Presenter provideDetailPresenter(){
        return new DetailPresenter(provideDetailModel());
    }





}
