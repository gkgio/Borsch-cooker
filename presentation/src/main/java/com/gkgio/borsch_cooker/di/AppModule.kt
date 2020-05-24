package com.gkgio.borsch_cooker.di

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.gkgio.data.DeviceInterceptor
import com.gkgio.data.errorreporter.ErrorReporterImpl
import com.gkgio.domain.errorreporter.ErrorReporter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import retrofit2.converter.moshi.MoshiConverterFactory
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import timber.log.Timber
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

@Module(includes = [AppModule.BindsModule::class])
class AppModule(private val app: Application) {
    private companion object {
        private const val HTTP_TIMEOUT = 30L
    }

    @Singleton
    @Provides
    fun provideContext(): Context = app.applicationContext

    @Singleton
    @Provides
    fun providePrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        deviceInterceptor: DeviceInterceptor
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpBuilder = OkHttpClient.Builder()
            .addInterceptor(deviceInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)

        // Андроид ругается на незащищенное тестовое апи, разрешаекм доступ к незащищенным ресурсам
        val trustManager = object : X509TrustManager {
            @SuppressLint("TrustAllX509TrustManager")
            override fun checkClientTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                // Empty
            }

            @SuppressLint("TrustAllX509TrustManager")
            override fun checkServerTrusted(p0: Array<out X509Certificate>?, p1: String?) {
                // Empty
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return emptyArray()
            }
        }

        val trustAllCerts = arrayOf(trustManager)

        try {
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            okHttpBuilder.sslSocketFactory(sslContext.socketFactory, trustManager)
        } catch (e: Exception) {
            Timber.e(e, "Error install ssl")
        }

        return okHttpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        moshi: Moshi
    ): Retrofit {
        return Retrofit.Builder()
            .client(client)
            .baseUrl("https://horoscopes.rambler.ru/api/front/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Module
    abstract inner class BindsModule {
        @Binds
        abstract fun bindErrorReporter(arg: ErrorReporterImpl): ErrorReporter
    }
}