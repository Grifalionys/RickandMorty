package com.example.astonproject.app.di

import android.app.Application
import com.grifalion.rickandmorty.app.App
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.di.AppModule
import com.grifalion.rickandmorty.presentation.MainActivity
import com.grifalion.rickandmorty.presentation.fragments.character.list.CharactersListFragment
import dagger.BindsInstance
import dagger.Component


@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(fragment: CharactersListFragment)
    fun inject(application: App)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }

}
