package org.bibletranslationtools.fetcher.web

import dev.jbs.ktor.thymeleaf.Thymeleaf
import dev.jbs.ktor.thymeleaf.ThymeleafContent
import io.ktor.application.Application
import io.ktor.application.ApplicationCallPipeline
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

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
    intercept(ApplicationCallPipeline.Setup) {
        // display error page here and terminate the pipeline if fatal exception occurs
    }
    install(Routing) {
        route("/") {
            get {
                call.respond(
                    ThymeleafContent(
                        template = "index",
                        model = mapOf()
                    )
                )
            }
            route("gl") {
                get {
                    call.respond(
                        ThymeleafContent(
                            template = "languages",
                            model = mapOf()
                        )
                    )
                }
                route("{languageCode}") {
                    get {
                        call.respond(
                            ThymeleafContent(
                                template = "products",
                                model = mapOf()
                            )
                        )
                    }
                    route("{productSlug}") {
                        get {
                            call.respond(
                                ThymeleafContent(
                                    template = "books",
                                    model = mapOf(
                                        "languageCode" to call.parameters["languageCode"]!!
                                    )
                                )
                            )
                        }
                        route("{bookSlug}") {
                            get {
                                call.respond(
                                    ThymeleafContent(
                                        template = "chapters",
                                        model = mapOf(
                                            "languageCode" to call.parameters["languageCode"]!!,
                                            "productSlug" to call.parameters["productSlug"]!!
                                        )
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
