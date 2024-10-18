package com.example.linguareader.repository

import com.example.linguareader.data.model.Book

class BookRepository {

    private val books = listOf(
        Book(id = "1", title = "Программирование на Kotlin", content = "Краткое введение в Kotlin."),
        Book(id = "2", title = "Основы Android", content = "Изучите основы разработки Android."),
        Book(id = "3", title = "Flutter для начинающих", content = "Создайте свои первые приложения на Flutter."),
        Book(id = "25", title = "Кроссплатформенное решение", content = "Обзор кроссплатформенных технологий.")
    )

    fun getBookById(bookId: String): Book? {
        return books.find { it.id == bookId }
    }

    fun getBooks(): List<Book> {
        return books
    }
}

