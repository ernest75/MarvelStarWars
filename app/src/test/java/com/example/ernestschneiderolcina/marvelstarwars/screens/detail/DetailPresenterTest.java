package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    // region constants ----------------------------------------------------------------------------

    // endregion constants -------------------------------------------------------------------------

    // region helper fields ------------------------------------------------------------------------

    @Mock
    DetailMVP.Model mModelMocked;

    @Mock
    DetailMVP.View mViewMocked;
    // end region helper fields --------------------------------------------------------------------

    DetailPresenter SUT;

    @Before
    public void setup() throws Exception {
        SUT = new DetailPresenter(mModelMocked);

    }

    @Test
    public void setView_correctViewPassed_correctviewReturned() throws Exception {
        // Arrange
        // Act
        SUT.setView(mViewMocked);
        // Assert
        assertThat(SUT.mView,is(mViewMocked));
    }

    @Test
    public void getCharacterRepo_modelInvoked() throws Exception {
        // Arrange
        SUT.setView(mViewMocked);
        // Act
        SUT.getCharacterToShow();
        // Assert
        verify(mModelMocked, times(1)).getCharacterFromIntent(null);

    }

    @Test
    public void showData_correctView_invoked() throws Exception {
        // Arrange
        SUT.setView(mViewMocked);
        // Act
        SUT.showData();
        // Assert
        verify(mViewMocked,times(1)).configView();
    }

    // region helper methods -----------------------------------------------------------------------

    // endregion helper methods --------------------------------------------------------------------

    // region helper classes -----------------------------------------------------------------------

    // end region helper classes -------------------------------------------------------------------


}