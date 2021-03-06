package org.bibletranslationtools.fetcher.repository

import org.bibletranslationtools.fetcher.data.Book
import org.bibletranslationtools.fetcher.data.Language

interface BookRepository {
    fun getBooks(languageCode: String, resourceId: String): List<Book>
    fun getBooks(language: Language, resourceId: String): List<Book>
    fun getBook(slug: String, languageCode: String = "en"): Book?
    fun getBook(slug: String, language: Language): Book?
}
