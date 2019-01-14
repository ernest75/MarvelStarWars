package com.example.ernestschneiderolcina.marvelstarwars.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ernestschneiderolcina.marvelstarwars.R;
import com.example.ernestschneiderolcina.marvelstarwars.repo.CharacterRepo;
import com.example.ernestschneiderolcina.marvelstarwars.screens.main.MainMVP;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.ViewHolder> {

    private List<CharacterRepo> mListCharacter;

    private MainMVP.Presenter mPresenter;

    private Context mContext;

    public CharacterListAdapter(List<CharacterRepo> mListCharacter, MainMVP.Presenter mPresenter, Context mContext) {
        this.mListCharacter = mListCharacter;
        this.mPresenter = mPresenter;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.characters_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final CharacterRepo characterRepo = mListCharacter.get(position);
        viewHolder.mCharacterName.setText(characterRepo.name);
        viewHolder.mCharacterUniverse.setText(characterRepo.universe);

        final RequestOptions options = new RequestOptions()
                .centerCrop()
                .error(R.drawable.star_wars_avatar)
                .placeholder(R.drawable.ic_file_download_black_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(mContext)
                .load(characterRepo.pictureUrl)
                .apply(options)
                .into(viewHolder.mCharacterPicture);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                mPresenter.onCharacterClicked(characterRepo, new Intent());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mListCharacter.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public ItemClickListener mItemClickListener;

        @BindView(R.id.tvName)
        TextView mCharacterName;

        @BindView(R.id.tvUniverse)
        TextView mCharacterUniverse;

        @BindView(R.id.ivCharacterPicture)
        ImageView mCharacterPicture;

        public void setItemClickListener(ItemClickListener itemClickListener){
            mItemClickListener = itemClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onClick(view,getAdapterPosition(),false);
        }

        @Override
        public boolean onLongClick(View view) {
            mItemClickListener.onClick(view,getAdapterPosition(),true);
            return true;
        }
    }

}

