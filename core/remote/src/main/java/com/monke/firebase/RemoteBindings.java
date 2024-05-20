package com.monke.firebase;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.FilesRepository;

import dagger.Binds;
import dagger.Module;

@Module
public interface RemoteBindings {
    @Binds
    FilesRepository bindFilesRepository(FilesRepositoryImpl i);

    @Binds
    FilesRemoteDataSource bindFilesRemoteDataSource(FilesRemoteDataSourceImpl i);
}
