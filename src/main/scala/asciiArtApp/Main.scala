package asciiArtApp

import asciiArtApp.ui.parsers.{ArgumentParser, ParsedArguments}
import asciiArtApp.ui.configs.{LoaderConfig, ConverterConfig, ExporterConfig}
import scala.util.{Failure, Success}

object Main {

  def main(args: Array[String]): Unit = {
    val parser = new ArgumentParser()

    try {
      val config = parser.parse(args)
      runApplication(config)
    } catch {
      case e: IllegalArgumentException =>
        println(s"Error: ${e.getMessage}")
        sys.exit(1)
      case e: Exception =>
        println(s"Unexpected error: ${e.getMessage}")
        e.printStackTrace()
        sys.exit(1)
    }
  }

  private def runApplication(config: ParsedArguments): Unit = {
    try {
      val loader = LoaderConfig.createLoader(config.imagePath, config.imageRandom)

      loader.loadImage() match {
        case Success(rgbImage) =>
          val processedRGB = config.filterConfig.applyRgbFilters(rgbImage)

          val rgbToGray = ConverterConfig.createRGBToGrayscaleConverter()
          val grayscale = rgbToGray.convert(processedRGB)

          val processedGrayscale = config.filterConfig.applyGrayscaleFilters(grayscale)

          val table = ConverterConfig.getTable(config.tableName, config.customTable)
          val grayToAscii = ConverterConfig.createGrayscaleToAsciiConverter(table)
          val ascii = grayToAscii.convert(processedGrayscale)

          val asciiText = ascii.toText
          ExporterConfig.exportResults(asciiText, config.outputConsole, config.outputFile)

        case Failure(exception) =>
          throw new RuntimeException(s"Failed to load image: ${exception.getMessage}", exception)
      }
    } catch {
      case e: Exception =>
        println(s"Application error: ${e.getMessage}")
        throw e
    }
  }
}
