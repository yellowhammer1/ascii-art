package asciiArtApp.ui.configs

import asciiArtApp.exporters.text.{ConsoleTextExporter, FileTextExporter}
import java.io.File

object ExporterConfig {
  def exportResults(asciiText: String, outputConsole: Boolean, outputFile: Option[String]): Unit = {
    if (outputConsole) {
      exportToConsole(asciiText)
    }

    outputFile.foreach { path =>
      exportToFile(asciiText, path)
    }
  }

  private def exportToConsole(text: String): Unit = {
    val exporter = new ConsoleTextExporter()
    try {
      exporter.output(text)
    } catch {
      case e: Exception =>
        throw new RuntimeException(s"Failed to output to console: ${e.getMessage}", e)
    } finally {
      exporter.close()
    }
  }

  private def exportToFile(text: String, path: String): Unit = {
    val file = new File(path)
    val exporter = new FileTextExporter(file)

    try {
      exporter.output(text)
    } catch {
      case e: Exception =>
        throw new RuntimeException(s"Failed to save to file '$path': ${e.getMessage}", e)
    } finally {
      exporter.close()
    }
  }
}
