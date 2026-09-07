package asciiArtApp.exporters

/**
 * Exports content to an output destination.
 *
 * @tparam T the type of content to export
 */
trait Exporter[T] {
  /**
   * Outputs the item to the destination.
   *
   * @param item the item to export
   */
  def output(item: T): Unit
}
