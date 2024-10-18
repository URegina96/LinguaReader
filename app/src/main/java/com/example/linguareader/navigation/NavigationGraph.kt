package com.example.linguareader.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.linguareader.Screen
import com.example.linguareader.repository.BookRepository
import com.example.linguareader.ux_ui.screen.*
@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val bookRepository = BookRepository()
    val lastBookId = "1" // Предположим, что книга с id = "1" — последняя прочитанная книга. Можно добавить логику для определения последней прочитанной книги.

    NavHost(navController = navController, startDestination = Screen.Home.route, modifier = modifier) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onContinueReadingClick = { navController.navigate(Screen.BookReading.createRoute(lastBookId)) },
                onBookListClick = { navController.navigate(Screen.BookList.route) },
                onAddBookClick = { navController.navigate(Screen.AddBook.route) },
                onSettingsClick = { navController.navigate(Screen.Settings.route) }
            )
        }

        composable(route = Screen.BookList.route) {
            BookListScreen(
                books = bookRepository.getBooks(),
                onBookClick = { book -> navController.navigate(Screen.BookReading.createRoute(book.id)) }
            )
        }

        composable(route = Screen.AddBook.route) {
            AddBookScreen(onBackClick = { navController.navigateUp() })
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(onBackClick = { navController.navigateUp() })
        }

        composable(route = Screen.BookReading.route + "/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId") ?: return@composable
            val book = bookRepository.getBookById(bookId)

            if (book != null) {
                BookReadingScreen(
                    longText = book.content,
                    onWordClicked = { word -> /* Обработка клика по слову */ }
                )
            }
        }
    }
}
