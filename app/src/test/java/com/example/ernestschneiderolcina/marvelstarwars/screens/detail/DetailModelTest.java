package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import android.content.Intent;

import com.example.ernestschneiderolcina.marvelstarwars.constants.Constants;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

import static org.junit.Assert.*;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentCaptor.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.anyString;

@RunWith(MockitoJUnitRunner.class)
public class DetailModelTest {


    // region constants ----------------------------------------------------------------------------

    public static final String NAME = "name";
    public static final String UNIVERSE = "universe";
    public static final String URL = "url";
    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    // end region helper fields --------------------------------------------------------------------

    DetailModel SUT;

    @Before
    public void setup() throws Exception {
        SUT = new DetailModel();

    }

    @Test
    public void getCharacterFromIntent_correctDataPassed_correctDataReturned() throws Exception {
        // Arrange
        IntentDouble intentDouble = new IntentDouble();
        intentDouble.putExtra(Constants.CHARACTER_NAME, NAME);
        intentDouble.putExtra(Constants.CHARACTER_UNIVERSE, UNIVERSE);
        intentDouble.putExtra(Constants.CHARACTER_URL, URL);
        // Act
        CharacterRepo result = SUT.getCharacterFromIntent(intentDouble);
        // Assert
        assertThat(result.name,is(NAME));
        assertThat(result.universe,is(UNIVERSE));
        assertThat(result.getPictureUrl(),is(URL));
    }

    @Test
    public void getCharacterFromIntent_wrongDataPassed_wrongDataReturned() throws Exception {
        // Arrange
        IntentDouble intentDouble = new IntentDouble();
        // Act
        CharacterRepo result = SUT.getCharacterFromIntent(intentDouble);
        // Assert
        assertThat(result.name,is(nullValue()));
        assertThat(result.universe,is(nullValue()));
        assertThat(result.getPictureUrl(),is(nullValue()));
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