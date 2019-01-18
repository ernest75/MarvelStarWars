package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.Intent;

import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

import java.util.List;

import io.reactivex.Observable;

public interface MainMVP {


    interface Model {

        Observable<CharacterRepo> getInfoFromStarWarsApi();

        Observable<CharacterRepo> getInfoFromDogApi();

    }

    interface Presenter {

        void setView(MainMVP.View view);

        void onCharacterClicked(CharacterRepo character, Intent intent);

        void loadData();

        void rxJavaUnsubscribe();


    }

    interface View {

        void showData(List<CharacterRepo> characterRepoList);

        void showProgressbar();

        void hideProgressbar();

        void showErrorFromNetwork(String message);

        void showMessage(String s);
    }
}
