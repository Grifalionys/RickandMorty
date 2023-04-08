package com.grifalion.rickandmorty.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationData(
    var name: String,
    var url: String
): Parcelable
