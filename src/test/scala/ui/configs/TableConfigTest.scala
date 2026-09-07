package ui.configs

import asciiArtApp.ui.configs.TableConfig
import asciiArtApp.converters.tables.predefined.{BourkeLinearTransformationTable, StandardLinearTransformationTable, HighContrastNonlinearTransformationTable}
import org.scalatest.funsuite.AnyFunSuite

class TableConfigTest extends AnyFunSuite {
  test("getTable returns Bourke table for 'bourke'") {
    val table = TableConfig.getTable("bourke")

    assert(table.contains(BourkeLinearTransformationTable))
  }

  test("getTable returns Standard table for 'standard'") {
    val table = TableConfig.getTable("standard")

    assert(table.contains(StandardLinearTransformationTable))
  }

  test("getTable returns HighContrast table for 'high-contrast'") {
    val table = TableConfig.getTable("high-contrast")

    assert(table.contains(HighContrastNonlinearTransformationTable))
  }

  test("getTable is case insensitive") {
    assert(TableConfig.getTable("BOURKE").contains(BourkeLinearTransformationTable))
    assert(TableConfig.getTable("Bourke").contains(BourkeLinearTransformationTable))
    assert(TableConfig.getTable("BoUrKe").contains(BourkeLinearTransformationTable))
  }

  test("getTable returns None for unknown table") {
    assert(TableConfig.getTable("unknown").isEmpty)
    assert(TableConfig.getTable("xyz").isEmpty)
    assert(TableConfig.getTable("").isEmpty)
  }

  test("createCustomTable creates table from valid characters") {
    val table = TableConfig.createCustomTable(".#@")

    assert(table.isDefined)
    table.foreach { t =>
      assert(t.transform(0) == '.')
      assert(t.transform(255) == '@')
    }
  }

  test("createCustomTable returns None for empty string") {
    val table = TableConfig.createCustomTable("")

    assert(table.isEmpty)
  }

  test("default returns Bourke table") {
    assert(TableConfig.default == BourkeLinearTransformationTable)
  }
}
