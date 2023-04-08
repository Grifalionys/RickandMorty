package com.grifalion.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.databinding.ActivityMainBinding
import com.grifalion.rickandmorty.presentation.fragments.character.CharactersFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.EpisodesFragment
import com.grifalion.rickandmorty.presentation.fragments.location.LocationsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()

    }
    private fun initBottomNav(){
        replaceFragment(CharactersFragment())
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.characters -> replaceFragment(CharactersFragment())
                R.id.locations -> replaceFragment(LocationsFragment())
                R.id.episodes -> replaceFragment(EpisodesFragment())
            }
            true
        }
    }


    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
        .beginTransaction()
        .replace(R.id.containerFragment,fragment)
        .commit()
    }
}