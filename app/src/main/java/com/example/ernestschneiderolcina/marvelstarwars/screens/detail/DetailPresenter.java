package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

public class DetailPresenter implements DetailMVP.Presenter{

    DetailMVP.Model mModel;

    DetailMVP.View mView;

    public DetailPresenter(DetailMVP.Model mModel) {
        this.mModel = mModel;
    }

    @Override
    public void setView(DetailMVP.View view) {
            mView = view;
    }

    @Override
    public CharacterRepo getCharacterToShow() {
        return mModel.getCharacterFromIntent(mView.getIntentCreator());
    }

    @Override
    public void showData() {
        mView.configView();
    }
}
