package asciiArtApp.converters.tables.predefined

import asciiArtApp.converters.tables.NonlinearTransformationTable

object HighContrastNonlinearTransformationTable extends NonlinearTransformationTable(
  "@*. ",
  Seq(0 to 99, 100 to 127, 128 to 155, 156 to 255)
) {}
