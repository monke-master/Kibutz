package com.example.map;

import com.yandex.mapkit.search.SearchFactory;
import com.yandex.mapkit.search.SearchManager;
import com.yandex.mapkit.search.SearchManagerType;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    SearchManager provideSearchManager() {
        return SearchFactory.getInstance().createSearchManager(SearchManagerType.ONLINE);
    }
}
