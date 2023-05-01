package com.grifalion.rickandmorty.domain.usecases

import android.app.Application
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val application: Application
) {
    fun getCharacter(name: String, status: String, gender: String, species: String): CharacterDataSource{
        return CharacterDataSource(repository,application,name, status,gender, species)
    }
}