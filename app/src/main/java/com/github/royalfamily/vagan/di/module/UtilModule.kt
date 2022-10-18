package com.github.royalfamily.vagan.di.module

import android.content.Context
import android.content.res.Resources
import com.github.royalfamily.vagan.ui.component.button.Button
import com.github.royalfamily.vagan.util.SizeUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilModule {

    @Singleton
    @Provides
    fun provideResource(@ApplicationContext context: Context): Resources = context.resources

}