package com.example.linguareader.ux_ui.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PdfViewModel : ViewModel() {
    private val _pdfText = mutableStateOf("Загрузка...")
    val pdfText: State<String> = _pdfText

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _progress = mutableStateOf(0f)
    val progress: State<Float> = _progress

    fun loadPdfText(context: Context, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            _progress.value = 0f

            val text = PdfUtils.extractTextFromPdf(context, uri) { progress ->
                _progress.value = progress
            }
            _pdfText.value = text
            _isLoading.value = false
        }
    }
}