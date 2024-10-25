import android.content.Context
import android.net.Uri
import android.util.Log
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class PdfUtils {
    companion object {
        suspend fun extractTextFromPdf(context: Context, uri: Uri, onProgressUpdate: (Float) -> Unit): String {
            return withContext(Dispatchers.IO) {
                try {
                    PDFBoxResourceLoader.init(context)
                    val inputStream = context.contentResolver.openInputStream(uri)!!
                    val file = File(context.cacheDir, "temp.pdf")
                    val fileSize = inputStream.available().toFloat()
                    var bytesRead = 0f

                    FileOutputStream(file).use { outputStream ->
                        val buffer = ByteArray(1024)
                        var read: Int
                        while (inputStream.read(buffer).also { read = it } != -1) {
                            outputStream.write(buffer, 0, read)
                            bytesRead += read
                            onProgressUpdate(bytesRead / fileSize)
                        }
                    }

                    PDDocument.load(file).use { document ->
                        val pdfStripper = PDFTextStripper()
                        pdfStripper.getText(document)
                    }
                } catch (e: Exception) {
                    Log.e("PdfUtils", "Error extracting text from PDF", e)
                    ""
                }
            }
        }
    }
}