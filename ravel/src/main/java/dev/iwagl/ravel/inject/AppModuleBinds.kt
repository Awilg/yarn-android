package dev.iwagl.ravel.inject

import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import dagger.multibindings.*
import dev.iwagl.ravel.appinits.*

@InstallIn(SingletonComponent::class)
@Module
abstract class AppModuleBinds {
    @Binds
    @IntoSet
    abstract fun provideLogger(bind: TimberInitializer): AppInitializer
}
