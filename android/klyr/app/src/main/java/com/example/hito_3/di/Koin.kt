package com.example.hito_3.di

import com.example.hito_3.data.BulletRewriter.ResumeBulletRewriterModel
import com.example.hito_3.data.FirestoreRepository
import com.example.hito_3.data.KlyrAPI
import com.example.hito_3.data.KlyrRepository
import com.example.hito_3.data.KtorKlyrApi
import com.example.hito_3.user_interface.Auth.LogUpViewModel
import com.example.hito_3.user_interface.Auth.LoginViewModel
import com.example.hito_3.user_interface.bullet.ResumeBulletRewriterViewModel
import com.example.hito_3.user_interface.resumeAnalyze.AnalyzeViewModel
import com.example.hito_3.user_interface.resume_JD_match.ResumeJDMatchViewModel
import com.example.hito_3.user_interface.sectionGenerator.ResumeSectionGeneratorViewModel
import com.example.hito_3.user_interface.setting.SettingsViewModel
import com.example.hito_3.user_interface.skillGap.SkillGapAnalyzeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
        }

        HttpClient {
            install(ContentNegotiation) {
                json(json , contentType = ContentType.Any)
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 60000  // 60 seconds
                connectTimeoutMillis = 120000  // 30 seconds
                socketTimeoutMillis = 60000   // 60 seconds
            }

        }
    }

    single<KlyrAPI> {
        KtorKlyrApi(get())
    }
    single<KlyrRepository> {
        KlyrRepository(get())
    }
    single<FirestoreRepository> {
        FirestoreRepository()
    }


}

val viewModelModule = module {
    factoryOf(::AnalyzeViewModel)
    factoryOf(::SkillGapAnalyzeViewModel)
    factoryOf(::ResumeSectionGeneratorViewModel)
    factoryOf(::ResumeJDMatchViewModel)
    factoryOf(::ResumeBulletRewriterViewModel)
    factoryOf(::LoginViewModel)
    factoryOf(::LogUpViewModel)
    factoryOf(::SettingsViewModel)
}

fun initkoin() {
    startKoin {
        modules(dataModule , viewModelModule)
    }
}