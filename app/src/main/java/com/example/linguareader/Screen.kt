package com.example.linguareader

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object BookList : Screen("bookList")
    object AddBook : Screen("addBook")
    object Settings : Screen("settings")
    object BookReading : Screen("bookReading")
    object PdfReader : Screen("pdf_reader")
    fun createRoute(bookId: String): String {
        return "bookReading/$bookId"
    }
}