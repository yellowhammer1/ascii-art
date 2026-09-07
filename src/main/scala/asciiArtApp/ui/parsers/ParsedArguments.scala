package asciiArtApp.ui.parsers

import asciiArtApp.ui.configs.FilterConfig

case class ParsedArguments( imagePath: Option[String] = None,
                            imageRandom: Boolean = false,
                            tableName: Option[String] = None,
                            customTable: Option[String] = None,
                            outputConsole: Boolean = false,
                            outputFile: Option[String] = None,
                            filterConfig: FilterConfig = FilterConfig())
