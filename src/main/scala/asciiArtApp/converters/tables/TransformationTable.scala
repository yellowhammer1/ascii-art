package asciiArtApp.converters.tables

/**
 * Maps grayscale intensity values (0-255) to ASCII characters.
 */
trait TransformationTable {
  val table: Seq[Char]

  /**
   * Transforms a grayscale intensity to an ASCII character.
   *
   * @param intensity the grayscale value (0-255)
   * @return the corresponding ASCII character
   */
  def transform(intensity: Int): Char
}
