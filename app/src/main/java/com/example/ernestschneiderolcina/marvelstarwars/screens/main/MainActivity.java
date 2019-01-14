package com.example.ernestschneiderolcina.marvelstarwars.screens.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ernestschneiderolcina.marvelstarwars.R;
import com.example.ernestschneiderolcina.marvelstarwars.adapters.CharacterListAdapter;
import com.example.ernestschneiderolcina.marvelstarwars.dagger.App;
import com.example.ernestschneiderolcina.marvelstarwars.networking.retrofitservices.StarWarsApiService;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainMVP.View {

    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;

    @BindView(R.id.rvCharacter)
    RecyclerView mRvCharacter;

    @Inject
    Context mContext;

    @Inject
    MainMVP.Presenter mPresenter;

    private CharacterListAdapter mListAdapter;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ((App) getApplication()).getApplicationComponent().inject(this);

        mPresenter.setView(this);
        mPresenter.loadData();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.rxJavaUnsubscribe();
        mPresenter.setView(null);

    }

    @Override
    public void showData(List<CharacterRepo> characterRepoList) {
        mListAdapter = new CharacterListAdapter(characterRepoList,mPresenter,mContext);
        mRvCharacter.setAdapter(mListAdapter);
        mRvCharacter.setLayoutManager(new LinearLayoutManager(mContext));

    }

    @Override
    public void showProgressbar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressbar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showErrorFromNetwork(String message) {
        Toast.makeText(mContext,"Server Error",Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String s) {
        Toast.makeText(mContext,s,Toast.LENGTH_LONG).show();
    }

}
