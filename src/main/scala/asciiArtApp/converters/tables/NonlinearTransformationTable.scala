package asciiArtApp.converters.tables

/**
 * A non-linear transformation table where grayscale values are divided
 * unevenly among characters. Each character has an associated range of intensity values.
 *
 * Example: Character '@' might represent 0-100, while ' ' represents 200-255.
 * This allows emphasizing certain intensity ranges.
 *
 * @param table the sequence of characters
 * @param ranges the intensity ranges for each character (must match table size)
 */
class NonlinearTransformationTable(override val table: Seq[Char], val ranges: Seq[Range]) extends TransformationTable {
  override def transform(intensity: Int): Char = {
    val index = ranges.indexWhere(_.contains(intensity))
    if (index == -1) {
      throw new IllegalArgumentException(s"Intensity $intensity not covered by ranges")
    }
    table(index)
  }
}

object NonlinearTransformationTable {
  /**
   * Creates a non-linear transformation table with validation.
   *
   * Requirements:
   * - table and ranges must have the same size
   * - ranges must be pairwise disjoint (no overlaps)
   * - ranges must cover exactly 0-255 (no gaps or extras)
   *
   * @param table  the sequence of characters
   * @param ranges the intensity ranges for each character
   * @return Some(table) if valid, None otherwise
   */
  def apply(table: Seq[Char], ranges: Seq[Range]): Option[NonlinearTransformationTable] = {
    if (table.nonEmpty && table.size <= 256 && table.size == ranges.size && validRanges(ranges))
      Some(new NonlinearTransformationTable(table, ranges))
    else None
  }

  /**
   * Validates that ranges are disjoint and cover exactly 0-255.
   */
  private def validRanges(ranges: Seq[Range]): Boolean = {
    // Check that ranges don't overlap
    val hasEmptyIntersections = ranges.combinations(2).forall {
      case Seq(r1, r2) => r1.intersect(r2).isEmpty
    }

    // Check that ranges cover exactly 0-255 with no gaps
    val allValues = ranges.flatMap(_.toSeq).toSet
    val coversFullInterval = allValues == (0 to 255).toSet

    hasEmptyIntersections && coversFullInterval
  }
}
