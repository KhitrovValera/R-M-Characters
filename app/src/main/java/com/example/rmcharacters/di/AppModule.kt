package com.example.rmcharacters.di

import android.content.Context
import androidx.room.Room
import com.example.rmcharacters.data.local.dao.CharactersDao
import com.example.rmcharacters.data.local.database.AppDatabase
import com.example.rmcharacters.data.local.source.CharacterLocalDataSource
import com.example.rmcharacters.data.local.source.impl.CharacterLocalDataSourceImpl
import com.example.rmcharacters.data.remote.api.CharactersService
import com.example.rmcharacters.data.remote.common.ApiCaller
import com.example.rmcharacters.data.remote.source.CharactersRemoteDataSource
import com.example.rmcharacters.data.remote.source.impl.CharactersRemoteDataSourceImpl
import com.example.rmcharacters.data.repository.AppRepositoryImpl
import com.example.rmcharacters.domain.repository.AppRepository
import com.example.rmcharacters.domain.useCase.GetCharacterDetail
import com.example.rmcharacters.domain.useCase.GetCharacterUseCase
import com.example.rmcharacters.domain.useCase.GetCharactersResponseUseCase
import com.example.rmcharacters.domain.useCase.GetCharactersUseCase
import com.example.rmcharacters.domain.useCase.GetEpisodesUseCase
import com.example.rmcharacters.domain.useCase.GetLocationUseCase
import com.example.rmcharacters.domain.useCase.GetNextPageUseCase
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val contentType = "application/json".toMediaType()

    @Provides
    @Singleton
    fun provideJson() = Json {
        ignoreUnknownKeys = true
    }

    @Provides
    @Singleton
    fun provideApiCaller(): ApiCaller {
        return ApiCaller()
    }

    @OptIn(InternalSerializationApi::class)
    @Provides
    @Singleton
    fun provide(
        json: Json
    ): CharactersService {
        return Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(CharactersService::class.java)
    }

    @Provides
    fun provideCharactersDao(appDatabase: AppDatabase): CharactersDao {
        return appDatabase.charactersDao()
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "rick_and_morty_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCharactersRemoteDataSource(
        charactersService: CharactersService,
        apiCaller: ApiCaller
    ): CharactersRemoteDataSource {
        return CharactersRemoteDataSourceImpl(charactersService, apiCaller)
    }

    @Provides
    @Singleton
    fun provideCharactersLocalDataSource(dao: CharactersDao): CharacterLocalDataSource {
        return CharacterLocalDataSourceImpl(dao)
    }

    @Provides
    @Singleton
    fun provideAppRepository(
        charactersRemoteDataSource: CharactersRemoteDataSource,
        characterLocalDataSource: CharacterLocalDataSource
    ): AppRepository {
        return AppRepositoryImpl(charactersRemoteDataSource, characterLocalDataSource)
    }

    @Provides
    @Singleton
    fun provideGetCharactersResponseUseCase(repository: AppRepository): GetCharactersResponseUseCase {
        return GetCharactersResponseUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetNextPageUseCase(repository: AppRepository): GetNextPageUseCase {
        return GetNextPageUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(
        getCharactersResponseUseCase: GetCharactersResponseUseCase,
        getNextPageUseCase: GetNextPageUseCase
    ): GetCharactersUseCase {
        return GetCharactersUseCase(getCharactersResponseUseCase, getNextPageUseCase)
    }

    @Provides
    @Singleton
    fun provideGetCharacterUseCase(repository: AppRepository): GetCharacterUseCase {
        return GetCharacterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(repository: AppRepository): GetLocationUseCase {
        return GetLocationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetEpisodesUseCase(repository: AppRepository): GetEpisodesUseCase {
        return GetEpisodesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetCharacterDetail(
        getCharacterUseCase: GetCharacterUseCase,
        getLocationUseCase: GetLocationUseCase,
        getEpisodesUseCase: GetEpisodesUseCase
    ): GetCharacterDetail {
        return GetCharacterDetail(
            getCharacterUseCase,
            getLocationUseCase,
            getEpisodesUseCase
        )
    }
}