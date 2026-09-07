package asciiArtApp.ui.configs

import asciiArtApp.converters.{BasicGrayscaleToAsciiConverter, BasicRGBToGrayscaleConverter}
import asciiArtApp.converters.tables.TransformationTable

object ConverterConfig {
  def createRGBToGrayscaleConverter(): BasicRGBToGrayscaleConverter = {
    new BasicRGBToGrayscaleConverter()
  }

  def createGrayscaleToAsciiConverter(table: TransformationTable): BasicGrayscaleToAsciiConverter = {
    new BasicGrayscaleToAsciiConverter(table)
  }

  def getTable(tableName: Option[String], customTable: Option[String]): TransformationTable = {
    (tableName, customTable) match {
      case (Some(name), _) =>
        TableConfig.getTable(name).getOrElse {
          println(s"Warning: Unknown table '$name', using default")
          TableConfig.default
        }

      case (_, Some(chars)) =>
        TableConfig.createCustomTable(chars).getOrElse {
          println(s"Warning: Invalid custom table '$chars', using default")
          TableConfig.default
        }

      case _ =>
        TableConfig.default
    }
  }
}
