package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.Context;

import com.example.ernestschneiderolcina.marvelstarwars.constants.Constants;
import com.example.ernestschneiderolcina.marvelstarwars.networking.apismodels.StarWarsCharacter;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.DogApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.MarvelApiService;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

import static io.reactivex.Observable.just;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityModelTest {

    // region constants ----------------------------------------------------------------------------

    public static final String DOG_API_RESULT_STRING = "DogApiResult";
    public static final String DOG_UNIVERSE = "Dog Universe";
    public static final String NAME = "name";
    public static final String STAR_WARS_UNIVERSE = "StarWars";


    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    @Mock
    Context mContextMock;

    MockWebServer mMockWebServer;

    @Mock
    StarWarsApiService mStarWarsApiServiceMocked;

    MarvelApiService mMarvelApiServiceMocked;

    @Mock
    DogApiService mDogApiServiceMocked;

    CharacterRepo mCharacterRepoMocked;

    List<String> mArrayString;

    List<CharacterRepo> mArrayCharacterRepo;

    List<StarWarsCharacter> mArrayStarWarCharacter;

    Observable<CharacterRepo> mObservableCharacterRepo;

    // end region helper fields --------------------------------------------------------------------

    MainActivityModel SUT;

    @Before
    public void setup() throws Exception {
        mMockWebServer = new MockWebServer();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(mMockWebServer.url("https://swapi.co/api/people/?format=json&search=a").toString())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mMockWebServer.enqueue(new MockResponse().setBody(Constants.JSON_RESPONSE));
        mStarWarsApiServiceMocked = retrofit.create(StarWarsApiService .class);
        mMarvelApiServiceMocked = retrofit.create(MarvelApiService .class);
        mDogApiServiceMocked = retrofit.create(DogApiService.class);

        mArrayString = new ArrayList<>();
        mArrayCharacterRepo = new ArrayList<>();
        mArrayStarWarCharacter = new ArrayList<>();

        mCharacterRepoMocked = new CharacterRepo("Luke","StarWars","somePictureUrl");

        SUT = new MainActivityModel(mStarWarsApiServiceMocked,mMarvelApiServiceMocked,mDogApiServiceMocked);

    }

    @Test
    public void getInfoFromStarWars_returnsObservable() throws Exception {
        // Arrange

        // Act
        mObservableCharacterRepo = SUT.getInfoFromStarWarsApi();
        // Assert
        assertThat(mObservableCharacterRepo,is(notNullValue()));

    }

    @Test
    public void getInfoFromStarWars_observableEmitsItems() throws Exception {
        // Arrange
        // Act
        mObservableCharacterRepo = SUT.getInfoFromDogApi();
        // Assert
        assertThat(mObservableCharacterRepo.elementAt(0),is(notNullValue()));
        assertThat(mObservableCharacterRepo.elementAt(5),is(notNullValue()));
    }

    @Test
    public void getInfoFromDogApi_returnObservable() throws Exception {
        // Arrange

        // Act
        mObservableCharacterRepo = SUT.getInfoFromDogApi();

        // Assert
        assertThat(mObservableCharacterRepo,is(notNullValue()));

    }

    @Test
    public void getInfoFromDogApi_observableEmitsItems() throws Exception {
        // Arrange
        // Act
        mObservableCharacterRepo = SUT.getInfoFromDogApi();
        // Assert
        assertThat(mObservableCharacterRepo.elementAt(0),is(notNullValue()));
        assertThat(mObservableCharacterRepo.elementAt(10),is(notNullValue()));
    }

    @Test
    public void transformDogApiToCharacterRepo_wrongDataPassed_emptyResultReturned() throws Exception {
        // Arrange
        // Act
        mArrayCharacterRepo = SUT.transformDogApiToCharacterRepo(mArrayString);
        // Assert
        assertThat(mArrayCharacterRepo.size(),is(0));
    }

    @Test
    public void transformDogApiToCharacterRepo_correctDataPassed_correctArrayReturned() throws Exception {
        // Arrange
        String dogApiResult = DOG_API_RESULT_STRING;
        mArrayString.add(dogApiResult);
        // Act
        mArrayCharacterRepo = SUT.transformDogApiToCharacterRepo(mArrayString);
        // Assert
        assertThat(mArrayCharacterRepo.size(),is(1));
    }

    @Test
    public void transformDogApiToCharacterRepo_correctDataPassed_correctUrlReturned() throws Exception {
        // Arrange
        String dogApiResult = DOG_API_RESULT_STRING;
        mArrayString.add(dogApiResult);
        // Act
        mArrayCharacterRepo = SUT.transformDogApiToCharacterRepo(mArrayString);
        // Assert
        assertThat(mArrayCharacterRepo.get(0).pictureUrl,is(DOG_API_RESULT_STRING));
    }

    @Test
    public void transformDogApiToCharacterRepo_correctDataPassed_correctUniverseReturned() throws Exception {
        // Arrange
        String dogApiResult = DOG_API_RESULT_STRING;
        mArrayString.add(dogApiResult);
        // Act
        mArrayCharacterRepo = SUT.transformDogApiToCharacterRepo(mArrayString);
        // Assert
        assertThat(mArrayCharacterRepo.get(0).universe,is(DOG_UNIVERSE));
    }

    @Test
    public void transformStarWarsApiToCharacterRepo_wrongDataPassed_emptyResultReturned() throws Exception {
        // Arrange
        // Act
        mArrayCharacterRepo = SUT.transformStarWarApiToCharacterRepo(mArrayStarWarCharacter);
        // Assert
        assertThat(mArrayCharacterRepo.size(),is(0));
    }

    @Test
    public void transformStarWarsApiToCharacterRepo_correctDataPassed_correctArrayReturned() throws Exception {
        // Arrange
        StarWarsCharacter starWarsCharacter = new StarWarsCharacter();
        mArrayStarWarCharacter.add(starWarsCharacter);
        // Act
        mArrayCharacterRepo = SUT.transformStarWarApiToCharacterRepo(mArrayStarWarCharacter);
        // Assert
        assertThat(mArrayCharacterRepo.size(),is(1));
    }

    @Test
    public void transformStarWarsApiToCharacterRepo_correctDataPassed_correctNameReturned() throws Exception {
        // Arrange
        StarWarsCharacter starWarsCharacter = new StarWarsCharacter();
        starWarsCharacter.setName(NAME);
        mArrayStarWarCharacter.add(starWarsCharacter);
        // Act
        mArrayCharacterRepo = SUT.transformStarWarApiToCharacterRepo(mArrayStarWarCharacter);
        // Assert
        assertThat(mArrayCharacterRepo.get(0).name,is(NAME));
    }

    @Test
    public void transformStarWarsApiToCharacterRepo_correctDataPassed_correctUniverseReturned() throws Exception {
        // Arrange
        StarWarsCharacter starWarsCharacter = new StarWarsCharacter();
        mArrayStarWarCharacter.add(starWarsCharacter);
        // Act
        mArrayCharacterRepo = SUT.transformStarWarApiToCharacterRepo(mArrayStarWarCharacter);
        // Assert
        assertThat(mArrayCharacterRepo.get(0).universe,is(STAR_WARS_UNIVERSE));
    }



    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    // end region helper classes -------------------------------------------------------------------


}