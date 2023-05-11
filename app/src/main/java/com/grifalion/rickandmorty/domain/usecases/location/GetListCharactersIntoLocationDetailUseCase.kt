package com.grifalion.rickandmorty.domain.usecases.location

import android.app.Application
import com.grifalion.rickandmorty.domain.models.character.CharacterResult
import com.grifalion.rickandmorty.domain.repository.LocationRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetListCharactersIntoLocationDetailUseCase @Inject constructor(
    private val repository: LocationRepository,
    private val application: Application
) {
    fun execute(id: String): Observable<List<CharacterResult>> {
        return repository.getListCharactersIntoLocationDetail(id)
    }
}