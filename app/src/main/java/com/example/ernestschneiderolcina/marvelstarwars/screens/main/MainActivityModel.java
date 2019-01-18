package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.Context;
import android.util.Log;

import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.DogApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.MarvelApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.DogPictureApi;
import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.StarWarsApi;
import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.StarWarsCharacter;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityModel implements MainMVP.Model {

    private static final String LOG_TAG = MainActivityModel.class.getSimpleName();

    StarWarsApiService mStarWarsApiService;

    MarvelApiService mMarvelPaiService;

    DogApiService mDogApiService;


    public MainActivityModel(StarWarsApiService starWarsApiService,
                             MarvelApiService marvelApiService,
                             DogApiService dogApiService) {
        this.mStarWarsApiService = starWarsApiService;
        this.mMarvelPaiService = marvelApiService;
        this.mDogApiService = dogApiService;

    }


    @Override
    public Observable<CharacterRepo> getInfoFromStarWarsApi() {

        Observable<CharacterRepo> starWarsCharacterObservable = mStarWarsApiService.getStarWarsCharacters("json")
                .concatMap(new Function<StarWarsApi, Observable<CharacterRepo>>() {
                    @Override
                    public Observable<CharacterRepo> apply(StarWarsApi starWarsApi) throws Exception {
                        return Observable.fromIterable(transformStarWarApiToCharacterRepo(starWarsApi.getResults()));
                    }
                }).take(20);
        return starWarsCharacterObservable;
    }

    @Override
    public Observable<CharacterRepo> getInfoFromDogApi() {
        Observable<CharacterRepo> dogPictureApiObservable = mDogApiService.getDogPictures()
                .concatMap(new Function<DogPictureApi, Observable<CharacterRepo>>() {
                    @Override
                    public Observable<CharacterRepo> apply(DogPictureApi dogPictureApi) throws Exception {
                        return Observable.fromIterable(transformDogApiToCharacterRepo(dogPictureApi.getDogPictures()));
                    }
                }).take(20);
        return dogPictureApiObservable;
    }



    //helper methods

    public List<CharacterRepo> transformDogApiToCharacterRepo(List<String> listToTransform){
        ArrayList<CharacterRepo> characterRepoArrayList = new ArrayList<>();
        int f = 1;
        for(String s : listToTransform){
            CharacterRepo characterRepo = new CharacterRepo(randomChar()+ "Dog " + Integer.toString(f),"Dog Universe",s);
            characterRepoArrayList.add(characterRepo);
            f++;
        }
        return characterRepoArrayList;
    }

    public List<CharacterRepo> transformStarWarApiToCharacterRepo(List<StarWarsCharacter> listToTransform){
        ArrayList<CharacterRepo> characterRepoArrayList = new ArrayList<>();
        for (StarWarsCharacter sTc : listToTransform){
            CharacterRepo characterRepo = new CharacterRepo(sTc.getName(),"StarWars","");
            characterRepoArrayList.add(characterRepo);
        }
        return characterRepoArrayList;
    }

    //create random char

    private String randomChar() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        char[] tempChar = {'e','m','z'};
        for (int i = 0; i < 1; i++){
            char charToAppend = tempChar[generator.nextInt(2)];
            randomStringBuilder.append(charToAppend);
        }
        return randomStringBuilder.toString();
    }




}
