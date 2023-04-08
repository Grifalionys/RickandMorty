package com.grifalion.rickandmorty.presentation.fragments.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.grifalion.rickandmorty.databinding.LocationFragmentBinding

class LocationsFragment: Fragment() {
    private lateinit var binding: LocationFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = LocationFragmentBinding.inflate(inflater)
        return binding.root
    }
}