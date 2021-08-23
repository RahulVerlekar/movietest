package com.rahulverlekar.moviedb.di

import com.rahulverlekar.data.caching.MovieUseCasesCachedImpl
import com.rahulverlekar.domain.usecase.movies.MovieUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object UseCasesModule {

    @Provides
    fun provideMovieUseCase(usecase: MovieUseCasesCachedImpl): MovieUseCase = usecase
}
