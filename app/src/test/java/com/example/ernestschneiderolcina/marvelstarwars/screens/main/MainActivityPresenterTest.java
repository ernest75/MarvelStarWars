package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Display;

import com.example.ernestschneiderolcina.marvelstarwars.constants.Constants;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.DogApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.MarvelApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;
import com.example.ernestschneiderolcina.marvelstarwars.screens.detail.DetailActivity;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.matchers.Any;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityPresenterTest {

    // region constants ----------------------------------------------------------------------------

    public static final String UNIVERSE = "UNIVERSE";
    public static final String URL = "URL";
    public static final String NAME_Z = "z";
    private static final String NAME_A = "a";

    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    @Mock
    Context mContextMock;

    MockWebServer mMockWebServer;

    StarWarsApiService mStarWarsApiServiceMocked;

    MarvelApiService mMarvelApiServiceMocked;

    DogApiService mDogApiServiceMocked;

    @Mock
    MainMVP.View mMainViewMocked;

    HashMap<String,String> mHashMap;
    
    MainMVP.Model mMainModelMocked;

    CharacterRepo mCharacterRepoMocked;

    @Mock
    Disposable mSubscriptionMocked;

    // end region helper fields --------------------------------------------------------------------

    MainActivityPresenter SUT;

    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(
                new Function<Callable<Scheduler>, Scheduler>() {

                    @Override
                    public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                    return Schedulers.trampoline();
                        }
                      });


    }

    @Before
    public void setup() throws Exception {
        mMockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mMockWebServer.url("").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mMockWebServer.enqueue(new MockResponse().setBody(Constants.JSON_RESPONSE));
        mStarWarsApiServiceMocked = retrofit.create(StarWarsApiService.class);
        mDogApiServiceMocked = retrofit.create(DogApiService.class);
        mMarvelApiServiceMocked = retrofit.create(MarvelApiService.class);
        mCharacterRepoMocked = new CharacterRepo("Luke","StarWars","somePictureUrl");
        mMainModelMocked = new MainActivityModel(mStarWarsApiServiceMocked, mMarvelApiServiceMocked,mDogApiServiceMocked);
        mHashMap = new HashMap<>();

        SUT = new MainActivityPresenter(mContextMock, mMainModelMocked);

    }


    @Test
    public void setView_correctViewAssigned() throws Exception {
        // Arrange
        // Act
        SUT.setView(mMainViewMocked);
        // Assert
        assertThat(SUT.mView,is(mMainViewMocked));

    }

    @Test
    public void onCharacterRepoClicked_correctDataPassedToIntent_correctDataStoredOnIntent() throws Exception {
        // Arrange
        IntentDouble intentDouble = new IntentDouble();
        intentDouble.putExtra(Constants.CHARACTER_NAME,mCharacterRepoMocked.getName());
        // Act
        SUT.onCharacterClicked(mCharacterRepoMocked,intentDouble);
        // Assert
        assertThat(intentDouble.getStringExtra(Constants.CHARACTER_NAME),is(mCharacterRepoMocked.getName()));
    }

    @Test
    public void onCharacterRepoClicked_correctDataPassed_startActivityCalled() throws Exception {
        // Arrange
        Intent intent = new IntentDouble();
        // Act
        SUT.onCharacterClicked(mCharacterRepoMocked,intent);
        // Assert
        verify(SUT.mContext,times(1)).startActivity(intent);
    }


    @Test
    public void rxJavaUnsubscribe_nullSubscription_doesNothing() throws Exception {
        // Arrange
        SUT.mSubscription = mSubscriptionMocked;
        // Act
        SUT.rxJavaUnsubscribe();
        // Assert
        verify( SUT.mSubscription,times(1)).dispose();

    }

    @Test
    public void orderAlphabetically_correctDataPassed_correctdataReturned() throws Exception {
        // Arrange
        ArrayList<CharacterRepo> arrayToSort = new ArrayList<>();
        ArrayList<CharacterRepo> result;
        arrayToSort.add(new CharacterRepo(NAME_Z, UNIVERSE, URL));
        arrayToSort.add(new CharacterRepo(NAME_A, UNIVERSE, URL));
        // Act
         result = (ArrayList<CharacterRepo>) SUT.orderAlphabetically(arrayToSort);
        // Assert
        assertThat(result.get(0).getName(),is(NAME_A));
        assertThat(result.get(1).getName(),is(NAME_Z));
    }

    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    public class IntentDouble extends Intent {

        public IntentDouble(){

        }

        HashMap<String,String> mHashMap = new HashMap<>();


        @Override
        public Intent putExtra(String name, String value) {

            mHashMap.put(name,value);
            return null;
    }


        @Override
        public String getStringExtra(String name) {
            return mHashMap.get(name);
        }
    }

    // end region helper classes -------------------------------------------------------------------


}