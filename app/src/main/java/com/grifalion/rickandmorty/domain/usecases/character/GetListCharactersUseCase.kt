package com.grifalion.rickandmorty.domain.usecases.character

import android.app.Application
import com.grifalion.rickandmorty.data.datasource.CharacterDataSource
import com.grifalion.rickandmorty.data.repository.CharacterRepositoryImpl
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(
    private val repository: CharacterRepositoryImpl,
    private val application: Application
) {
    fun execute(name: String, status: String, gender: String, species: String): CharacterDataSource{
        return CharacterDataSource(repository,application,name, status,gender, species)
    }
}