package com.example.ernestschneiderolcina.marvelstarwars.screens.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ernestschneiderolcina.marvelstarwars.R;
import com.example.ernestschneiderolcina.marvelstarwars.dagger.App;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity implements DetailMVP.View {

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.tvName)
    TextView tvName;

    @BindView(R.id.tvUniverse)
    TextView tvUniverse;

    @Inject
    Context mContext;

    @Inject
    DetailMVP.Presenter mPresenter;

    private Intent mIntent;

    private CharacterRepo mCharacterRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        ((App) getApplication()).getApplicationComponent().inject(this);
        mIntent = getIntent();

        mPresenter.setView(this);

        mCharacterRepo = mPresenter.getCharacterToShow();

    }

    @Override
    public Intent getIntentCreator() {
        return mIntent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.showData();
    }

    @Override
    protected void onStop() {
        mPresenter.setView(null);
        super.onStop();

    }

    @Override
    public void configView() {
        tvName.setText(mCharacterRepo.name);
        tvUniverse.setText(mCharacterRepo.universe);
        final RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.star_wars_avatar)
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(mContext)
                .load(mCharacterRepo.pictureUrl)
                .apply(options)
                .into(imageView);
    }
}
