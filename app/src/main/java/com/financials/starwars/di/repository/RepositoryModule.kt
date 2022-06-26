package com.financials.starwars.di.repository

import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSource
import com.financials.starwars.business.datasource.remote.remotesource.characterdetail.CharacterDetailRemoteSourceImpl
import com.financials.starwars.business.datasource.remote.remotesource.charactersearch.CharacterSearchRemoteSource
import com.financials.starwars.business.datasource.remote.remotesource.charactersearch.CharacterSearchRemoteSourceImpl
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepository
import com.financials.starwars.business.repository.characterdetail.CharacterDetailRepositoryImpl
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepository
import com.financials.starwars.business.repository.charactersearch.CharactersSearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideCharacterDetailRepository (
        characterDetailRepositoryImpl: CharacterDetailRepositoryImpl
    ) : CharacterDetailRepository


    @Binds
    abstract fun provideCharacterDetailsRemoteSource (
        characterDetailRemoteSourceImpl: CharacterDetailRemoteSourceImpl
    ) : CharacterDetailRemoteSource

    @Binds
    abstract fun provideCharactersSearchRepository (
        charactersSearchRepositoryImpl: CharactersSearchRepositoryImpl
    ) : CharactersSearchRepository

    @Binds
    abstract fun provideCharactersSearchRemoteSource (
        characterSearchRemoteSourceImpl: CharacterSearchRemoteSourceImpl
    ) : CharacterSearchRemoteSource

}