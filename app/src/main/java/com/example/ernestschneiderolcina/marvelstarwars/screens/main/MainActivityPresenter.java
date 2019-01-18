package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.ernestschneiderolcina.marvelstarwars.R;
import com.example.ernestschneiderolcina.marvelstarwars.constants.Constants;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivityPresenter implements MainMVP.Presenter {

    public MainMVP.View mView;

    MainMVP.Model mModel;

    Context mContext;

    List<CharacterRepo> mCharacterRepoList= new ArrayList<>();

    public Disposable mSubscription = null;

    private String LOG_TAG = MainActivityPresenter.class.getSimpleName();


    public MainActivityPresenter(Context context, MainMVP.Model model) {
        this.mContext = context;
        this.mModel = model;

        }

    @Override
    public void setView(MainMVP.View view) {
        mView = view;
    }

    @Override
    public void onCharacterClicked(CharacterRepo character,Intent intent) {
        intent.putExtra(Constants.CHARACTER_NAME, character.name);
        intent.putExtra(Constants.CHARACTER_UNIVERSE,character.universe);
        intent.putExtra(Constants.CHARACTER_URL,character.pictureUrl);
        mContext.startActivity(intent);

    }

    @Override
    public void loadData() {
        mCharacterRepoList.clear();

        mSubscription = io.reactivex.Observable.merge(mModel.getInfoFromStarWarsApi(),mModel.getInfoFromDogApi())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<CharacterRepo>() {
                    @Override
                    public void onNext(CharacterRepo characterRepo) {
                        mCharacterRepoList.add(characterRepo);

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showErrorFromNetwork(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        mCharacterRepoList = orderAlphabetically(mCharacterRepoList);
                        mView.showData(mCharacterRepoList);
                        mView.showMessage(mContext.getString(R.string.download_completed));
                        mView.hideProgressbar();
                    }
                }
               );

    }


    @Override
    public void rxJavaUnsubscribe() {
        if(mSubscription!=null && !mSubscription.isDisposed()){
            mSubscription.dispose();
        }
    }

    //helper methods

    public List<CharacterRepo> orderAlphabetically(List<CharacterRepo> characterRepoList) {
        ArrayList<String> characterRepoName = new ArrayList<>();
        for(CharacterRepo characterRepo : characterRepoList){
            characterRepoName.add(characterRepo.name);
        }
        Collections.sort(characterRepoName, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });

        List<CharacterRepo>characterRepoListSorted = new ArrayList<>();

        for(String s :characterRepoName){
            for(CharacterRepo characterRepo : characterRepoList){
                if(s.compareTo(characterRepo.name)==0){
                    characterRepoListSorted.add(characterRepo);
                }
            }
        }
        return characterRepoListSorted;
    }

}


