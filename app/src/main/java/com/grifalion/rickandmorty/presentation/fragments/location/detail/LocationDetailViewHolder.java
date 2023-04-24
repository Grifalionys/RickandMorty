package com.grifalion.rickandmorty.presentation.fragments.location.detail;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.grifalion.rickandmorty.R;

public class LocationDetailViewHolder extends RecyclerView.ViewHolder {

    TextView tvName, tvSpecies, tvStatus, tvGender;
    ShapeableImageView tvImage;
    public LocationDetailViewHolder(View view) {
        super(view);
        tvName = view.findViewById(R.id.tvName);
        tvImage = view.findViewById(R.id.imIconCharacter);
        tvSpecies = view.findViewById(R.id.tvSpecies);
        tvStatus = view.findViewById(R.id.tvStatus);
        tvGender = view.findViewById(R.id.tvGender);
    }
}
