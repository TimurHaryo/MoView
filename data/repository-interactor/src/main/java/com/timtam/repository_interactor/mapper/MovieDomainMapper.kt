package com.timtam.repository_interactor.mapper

import com.timtam.common_kotlin.extension.isTrue
import com.timtam.common_kotlin.extension.orZero
import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.repository.mapper.DomainMapper
import com.timtam.wrapper.model.MovieModel

class MovieDomainMapper : DomainMapper<MovieListDTO, List<MovieModel>> {

    override fun mapToDomainModel(dto: MovieListDTO): List<MovieModel> =
        dto.movies?.mapNotNull {
            it?.run {
                MovieModel(
                    id = id,
                    isAdult = isAdult.isTrue(),
                    backdropPath = backdropPath.orEmpty(),
                    genreIds = genreIds.orEmpty(),
                    originalTitle = originalTitle.orEmpty(),
                    posterPath = posterPath.orEmpty(),
                    releaseDate = releaseDate.orEmpty(),
                    title = title.orEmpty(),
                    isVideo = isVideo.isTrue(),
                    voteAverage = voteAverage.orZero(),
                    voteCount = voteCount.orZero()
                )
            }
        }.orEmpty()
}
