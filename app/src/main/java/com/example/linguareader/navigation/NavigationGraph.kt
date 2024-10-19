package com.example.linguareader.navigation

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.linguareader.Screen
import com.example.linguareader.data.model.Book
import com.example.linguareader.repository.BookRepository
import com.example.linguareader.utils.PdfUtils
import com.example.linguareader.ux_ui.screen.*

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    val bookRepository = BookRepository()
    val lastBookId = "1" // Предполагаем, что книга с id = "1" — последняя прочитанная книга.

    val context = LocalContext.current

    // Создаем launcher в контексте Composable
    val openPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                // Вызов метода для извлечения текста из PDF
                handlePdfSelected(context, it, bookRepository, navController)
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

private fun handlePdfSelected(
    context: Context,
    uri: Uri,
    bookRepository: BookRepository,
    navController: NavHostController
) {
    // Извлекаем текст из PDF
    val extractedText = PdfUtils.extractTextFromPdf(context, uri)

    // Извлекаем название файла из URI
    val fileName = getFileName(context, uri)

    // Если имя файла не удалось извлечь, используем "Unknown PDF"
    val bookTitle = fileName ?: "Unknown PDF"

    val newBook = Book(id = "pdf_${System.currentTimeMillis()}", title = bookTitle, content = extractedText)
    bookRepository.addBook(newBook) // Добавление новой книги
    navController.navigate(Screen.BookList.route) // Переход на экран списка книг
}

private fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null

    // Проверяем, что URI использует контент-провайдер
    if (uri.scheme == "content") {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            if (it.moveToFirst() && nameIndex != -1) {
                fileName = it.getString(nameIndex)
            }
        }
    } else {
        // Если URI не содержит контент-провайдера, извлекаем имя файла из пути
        fileName = uri.lastPathSegment
    }

    return fileName
}
