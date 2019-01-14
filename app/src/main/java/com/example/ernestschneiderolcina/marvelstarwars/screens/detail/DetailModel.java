package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import android.content.Intent;

import com.example.ernestschneiderolcina.marvelstarwars.constants.Constants;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

public class DetailModel implements DetailMVP.Model {

    @Override
    public CharacterRepo getCharacterFromIntent(Intent intent) {
        return new CharacterRepo(intent.getStringExtra(Constants.CHARACTER_NAME),
                intent.getStringExtra(Constants.CHARACTER_UNIVERSE),
                intent.getStringExtra(Constants.CHARACTER_URL));
    }
}
