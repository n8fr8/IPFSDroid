package org.ligi.ipfsdroid;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

@Module
public class AppModule {

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Singleton
    @Provides
    IPFSExecutor provideBinaryController() {
        return new IPFSExecutor(app);
    }

    @Singleton
    @Provides
    OkHttpClient proviceOkhttp() {
        return new OkHttpClient.Builder().build();
    }
}
