package org.bibletranslationtools.fetcher.web

import dev.jbs.ktor.thymeleaf.Thymeleaf
import dev.jbs.ktor.thymeleaf.ThymeleafContent
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.resources
import io.ktor.http.content.static
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.routing
import org.bibletranslationtools.fetcher.data.Language
import org.bibletranslationtools.fetcher.impl.repository.DirectoryProviderImpl
import org.bibletranslationtools.fetcher.impl.repository.LanguageRepositoryImpl
import org.bibletranslationtools.fetcher.impl.repository.PortGatewayLanguageCatalog
import org.bibletranslationtools.fetcher.impl.repository.StorageAccessImpl
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import javax.sound.sampled.Port

fun Application.appModule() {
    install(DefaultHeaders)
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    install(CallLogging)
    install(Routing) {
        routing {
            static("static") {
                resources("css")
                static("img") {
                    resources("img")
                }
            }
            get("/languages") {
                val languageCatalog = PortGatewayLanguageCatalog()
                val storageAccess = StorageAccessImpl(DirectoryProviderImpl())
                val languageList = LanguageRepositoryImpl(
                    storageAccess,
                    languageCatalog
                ).getLanguages()

                call.respond(
                    ThymeleafContent(
                        template = "languages",
                        model = mapOf(
                            "languageList" to languageList
                        )
                    )
                )
            }
            get("/products") {
                call.respond(
                    ThymeleafContent(
                        template = "products",
                        model = mapOf(

                        )
                    )
                )
            }
        }
    }
}
