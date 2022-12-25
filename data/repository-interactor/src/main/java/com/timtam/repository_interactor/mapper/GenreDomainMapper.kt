package com.timtam.repository_interactor.mapper

import com.timtam.common_kotlin.extension.orZero
import com.timtam.dto.model.genre.GenreListDTO
import com.timtam.repository.mapper.DomainMapper
import com.timtam.wrapper.model.GenreModel
import javax.inject.Inject

class GenreDomainMapper @Inject constructor() : DomainMapper<GenreListDTO, List<GenreModel>> {

    override fun mapToDomainModel(dto: GenreListDTO): List<GenreModel> =
        dto.genres?.mapNotNull {
            it?.run {
                GenreModel(
                    id = id.orZero(),
                    type = name.orEmpty()
                )
            }
        }.orEmpty()
}
