package com.timtam.repository.mapper

interface DomainMapper<DTO, DomainModel> {

    fun mapToDomainModel(dto: DTO): DomainModel
}
