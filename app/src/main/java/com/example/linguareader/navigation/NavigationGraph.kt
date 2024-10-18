package com.example.linguareader.navigation

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
    val lastBookId = "1" // Предполагаем, что книга с id = "1" — последняя прочитанная книга.

    // Локальная переменная для обработки открытия PDF
    val openPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val filePath = it.path ?: return@let // Обработка случая, когда путь может быть null
                navController.navigate(Screen.PdfReader.createRoute(filePath))
            }
        }
    )

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
                onBookClick = { book -> navController.navigate(Screen.BookReading.createRoute(book.id)) },
                onOpenPdfClick = { openPdfLauncher.launch("application/pdf") } // Передача функции открытия PDF
            )
        }

        composable(route = Screen.AddBook.route) {
            AddBookScreen(
                onBackClick = { navController.navigateUp() },
                onOpenPdfClick = { openPdfLauncher.launch("application/pdf") } // Передача функции открытия PDF
            )
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

        // Добавьте новый экран для PDF
        composable(route = Screen.PdfReader.route + "/{filePath}") { backStackEntry ->
            val filePath = backStackEntry.arguments?.getString("filePath")
            if (filePath != null) {
                PdfReaderScreen(filePath = filePath, onBackClick = { navController.navigateUp() })
            } else {
                navController.navigateUp() // Возврат назад, если filePath не найден
            }
        }
    }
}