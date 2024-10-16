package com.example.linguareader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.linguareader.ui.theme.LinguaReaderTheme
import com.example.linguareader.ux_ui.screen.BookReadingScreen

class MainActivity : ComponentActivity() {
    // Пример длинного текста для тестирования
    private val exampleLongText = """
        В 2024 году разработка приложений для Android продолжает эволюционировать, предлагая разработчикам новые инструменты и возможности. 
        С каждым годом Google внедряет новые функции и улучшения, которые делают процесс разработки более эффективным и удобным. 
        Одним из ключевых направлений является использование Jetpack Compose, который позволяет создавать интерфейсы пользователя 
        декларативным способом, упрощая работу с UI-компонентами. Это значительно ускоряет процесс разработки и улучшает 
        производительность приложений. 

        Кроме того, в 2024 году акцент на производительность приложений продолжает оставаться важным аспектом. 
        Разработчики должны учитывать оптимизацию своих приложений для различных устройств и экранов, чтобы обеспечить 
        лучший пользовательский опыт. Использование новых API и библиотек, таких как Hilt для внедрения зависимостей и 
        Room для работы с базами данных, помогает разработчикам создавать более надежные и производительные приложения. 

        Безопасность также остается в центре внимания. В 2024 году Android предлагает новые инструменты для защиты данных 
        пользователей и повышения уровня безопасности приложений. Разработчики должны быть внимательны к вопросам 
        конфиденциальности и использовать все доступные методы для защиты личной информации своих пользователей. 

        Важным аспектом разработки является работа с облачными сервисами. Google Cloud и Firebase предоставляют 
        множество возможностей для хранения данных, аутентификации пользователей и анализа данных. Это позволяет 
        разработчикам сосредоточиться на создании функциональности приложений, не беспокоясь о серверной части. 

        В 2024 году также наблюдается рост популярности кроссплатформенных решений, таких как Flutter и React Native. 
        Это позволяет разработчикам создавать приложения для Android и iOS с использованием одного кода, что значительно 
        экономит время и ресурсы. Однако важно учитывать, что каждый подход имеет свои преимущества и недостатки, 
        и выбор платформы зависит от конкретных требований проекта. 

        В целом, разработка под Android в 2024 году предлагает множество возможностей и вызовов. Разработчики должны 
        быть готовы к изменениям и адаптироваться к новым технологиям, чтобы оставаться конкурентоспособными на рынке. 
        Инновации и постоянное обучение станут ключевыми факторами успеха в этой быстро меняющейся области.
    """

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinguaReaderTheme {
                BookReadingScreen(
                    longText = exampleLongText,
                    onWordClicked = { word ->
                        // Обработка нажатия на слово, например, открытие диалогового окна с переводом
                        println("Вы выбрали слово: $word")
                    }
                )
            }
        }
    }
}