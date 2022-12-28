package com.timtam.repository_interactor.mapper

import com.timtam.common_kotlin.extension.isTrue
import com.timtam.common_kotlin.extension.orZero
import com.timtam.dto.model.movie.MovieListDTO
import com.timtam.repository.mapper.DomainMapper
import com.timtam.repository_interactor.util.Constants
import com.timtam.wrapper.model.MovieModel
import javax.inject.Inject

class MovieDomainMapper @Inject constructor() : DomainMapper<MovieListDTO, List<MovieModel>> {

    override fun mapToDomainModel(dto: MovieListDTO): List<MovieModel> =
        dto.movies?.mapNotNull {
            it?.run {
                MovieModel(
                    id = id,
                    isAdult = isAdult.isTrue(),
                    backdropPath = "${Constants.IMAGE_DOMAIN_URL}${backdropPath.orEmpty()}",
                    genreIds = genreIds.orEmpty(),
                    originalTitle = originalTitle.orEmpty(),
                    posterPath = "${Constants.IMAGE_DOMAIN_URL}${posterPath.orEmpty()}",
                    releaseDate = releaseDate.orEmpty(),
                    title = title.orEmpty(),
                    isVideo = isVideo.isTrue(),
                    voteAverage = voteAverage.orZero(),
                    voteCount = voteCount.orZero()
                )
            }
        }.orEmpty()
}
