package asciiArtApp.converters.tables

/**
 * A linear transformation table where the 256 grayscale values
 * are divided equally among the characters in the table.
 *
 * Example: With 10 characters, each character represents ~25.6 intensity levels.
 *
 * @param table the sequence of characters from darkest to lightest
 */
class LinearTransformationTable(override val table: Seq[Char]) extends TransformationTable {
  override def transform(intensity: Int): Char = {
    val clampedIntensity = intensity.max(0).min(255)

    // Map intensity (0-255) linearly to table indices
    // Formula: intensity / 255 * (table.size - 1)
    val charRange = 256.0 / table.size
    val idx = math.min(
      (clampedIntensity / charRange).toInt,
      table.size - 1
    )
    table(idx)
  }
}

object LinearTransformationTable {
  /**
   * Creates a linear transformation table with validation.
   *
   * @param table the sequence of characters (must be non-empty and ≤ 256 chars)
   * @return Some(table) if valid, None otherwise
   */
  def apply(table: Seq[Char]): Option[LinearTransformationTable] = {
    if (table.nonEmpty && table.size <= 256) Some(new LinearTransformationTable(table))
    else None
  }
}
