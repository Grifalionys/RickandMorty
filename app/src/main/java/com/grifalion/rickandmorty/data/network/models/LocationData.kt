package com.grifalion.rickandmorty.data.network.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationData(
    var name: String,
    var url: String
): Parcelable
