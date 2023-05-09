package com.grifalion.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.grifalion.rickandmorty.R
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.databinding.ActivityMainBinding
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharactersListFragment
import com.grifalion.rickandmorty.presentation.fragments.episode.list.EpisodeListFragment
import com.grifalion.rickandmorty.presentation.fragments.location.list.LocationsListFragment

class MainActivity : FragmentActivity() {
    private lateinit var binding: ActivityMainBinding

    private val component by lazy {
        (application as App).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNav()

    }
    private fun initBottomNav(){
        replaceFragment(CharactersListFragment())
        binding.bottomNavigationView.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.characters -> replaceFragment(CharactersListFragment())
                R.id.locations -> replaceFragment(LocationsListFragment())
                R.id.episodes -> replaceFragment(EpisodeListFragment())
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

    private fun hideBottomNavigation(){
        binding.bottomNavigationView.visibility = View.GONE
    }

    private fun showBottomNavigation(){
        binding.bottomNavigationView.visibility = View.VISIBLE
    }
}