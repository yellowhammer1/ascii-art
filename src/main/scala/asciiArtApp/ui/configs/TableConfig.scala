package asciiArtApp.ui.configs

import asciiArtApp.converters.tables.predefined.*
import asciiArtApp.converters.tables.{LinearTransformationTable, TransformationTable}

/**
 * Config for creating transformation tables.
 */
object TableConfig {
  /**
   * Returns a predefined table by name (case-insensitive).
   * Available tables: "bourke", "standard", "high-contrast"
   *
   * @param name the table name
   * @return Some(table) if found, None otherwise
   */
  def getTable(name: String): Option[TransformationTable] = name.toLowerCase match {
    case "bourke" => Some(BourkeLinearTransformationTable)
    case "standard" => Some(StandardLinearTransformationTable)
    case "high-contrast" => Some(HighContrastNonlinearTransformationTable)
    case _ => None
  }

  /**
   * Creates a custom linear transformation table from a string of characters.
   * Characters should be ordered from darkest to lightest representation.
   *
   * @param characters the characters to use (e.g., ".:-=+*#%@")
   * @return Some(table) if valid, None if empty
   */
  def createCustomTable(characters: String): Option[TransformationTable] = {
    LinearTransformationTable(characters.toSeq)
  }
  
  def default: TransformationTable = BourkeLinearTransformationTable
}
