package com.rom.moxo

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.rom.moxo.data.db.BlogDatabase
import com.rom.moxo.data.network.*
import com.rom.moxo.data.repository.BlogRepository
import com.rom.moxo.data.repository.BlogRepositoryImpl
import com.rom.moxo.ui.blog.BlogViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class BlogApplication: Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@BlogApplication))

        bind() from this.singleton { BlogDatabase(this.instance()) }
        bind() from this.singleton { this.instance<BlogDatabase>().blogContentDao() }
        this.bind<ConnectivityInterceptor>() with  this.singleton { ConnectivityInterceptorImpl(this.instance())}
        bind() from this.singleton { ApiInterface(this.instance()) }
        this.bind<BlogNetworkDataSource>() with  this.singleton { BlogNetworkDataSourceImpl(this.instance())}
        this.bind<BlogRepository>() with  this.singleton { BlogRepositoryImpl(this.instance(),this.instance())}
        bind() from this.provider { BlogViewModelFactory(this.instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}