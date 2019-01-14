package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import android.content.Intent;

import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

public interface DetailMVP {

    interface View{

        Intent getIntentCreator();

        void configView();

    }

    interface Model{

        CharacterRepo getCharacterFromIntent(Intent intent);

    }

    interface Presenter{

        void setView(DetailMVP.View view);

        CharacterRepo getCharacterToShow();

        void showData();

    }
}
