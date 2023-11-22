package com.vt.sentuhdigitalteknologitest.core.di;

import com.vt.sentuhdigitalteknologitest.core.api.ApiService;
import com.vt.sentuhdigitalteknologitest.core.repository.JokesRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module(includes = NetworkModule.class)
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public JokesRepository provideJokesRepository(ApiService apiService) {
        return new JokesRepository(apiService);
    }
}
