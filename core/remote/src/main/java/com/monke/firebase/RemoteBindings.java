package com.monke.firebase;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.FilesRepository;
import com.monke.data.ImageUploader;

import dagger.Binds;
import dagger.Module;

@Module
public interface RemoteBindings {

    @Binds
    ImageUploader bindImageUploader(ImageUploaderImpl i);

    @Binds
    FilesRepository bindFilesRepository(FilesRepositoryImpl i);

    @Binds
    FilesRemoteDataSource bindFilesRemoteDataSource(FilesRemoteDataSourceImpl i);
}
