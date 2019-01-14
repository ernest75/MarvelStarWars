package com.example.ernestschneiderolcina.marvelstarwars.dagger;

import com.example.ernestschneiderolcina.marvelstarwars.screens.detail.DetailActivity;
import com.example.ernestschneiderolcina.marvelstarwars.screens.main.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, NetworkingModule.class})
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);

    void inject(DetailActivity detailActivity);
}
