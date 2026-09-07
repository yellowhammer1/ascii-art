package ui.parsers

import asciiArtApp.ui.parsers.ArgumentParser
import org.scalatest.funsuite.AnyFunSuite

class ArgumentParserTest extends AnyFunSuite {

  val parser = new ArgumentParser()

  test("parse valid image path") {
    val args = Array("--image", "test.jpg", "--output-console")
    val result = parser.parse(args)

    assert(result.imagePath.contains("test.jpg"))
    assert(result.outputConsole)
  }

  test("parse image-random") {
    val args = Array("--image-random", "--output-console")
    val result = parser.parse(args)

    assert(result.imageRandom)
    assert(result.outputConsole)
  }

  test("parse table name") {
    val args = Array("--image", "test.jpg", "--table", "bourke", "--output-console")
    val result = parser.parse(args)

    assert(result.tableName.contains("bourke"))
  }

  test("parse custom table") {
    val args = Array("--image", "test.jpg", "--custom-table", ".#@", "--output-console")
    val result = parser.parse(args)

    assert(result.customTable.contains(".#@"))
  }

  test("parse multiple outputs") {
    val args = Array("--image", "test.jpg", "--output-console", "--output-file", "out.txt")
    val result = parser.parse(args)

    assert(result.outputConsole)
    assert(result.outputFile.contains("out.txt"))
  }

  test("parse rotate filter") {
    val args = Array("--image", "test.jpg", "--rotate", "90", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse rotation with minus sign") {
    val args = Array("--image", "test.jpg", "--rotate", "-90", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse scale filter") {
    val args = Array("--image", "test.jpg", "--scale", "0.25", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse flip filter") {
    val args = Array("--image", "test.jpg", "--flip", "x", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse case insensitive axis") {
    val args = Array("--image", "test.jpg", "--flip", "Y", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse font-aspect-ratio filter") {
    val args = Array("--image", "test.jpg", "--font-aspect-ratio", "1:2", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 1)
  }

  test("parse brightness filter") {
    val args = Array("--image", "test.jpg", "--brightness", "+10", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.grayscaleFilters.length == 1)
  }

  test("parse negative brightness") {
    val args = Array("--image", "test.jpg", "--brightness", "-10", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.grayscaleFilters.length == 1)
  }

  test("parse invert filter") {
    val args = Array("--image", "test.jpg", "--invert", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.grayscaleFilters.length == 1)
  }

  test("parse multiple filters in order") {
    val args = Array("--image", "test.jpg", "--rotate", "90", "--scale", "4.0", "--flip", "y", "--invert", "--output-console")
    val result = parser.parse(args)

    assert(result.filterConfig.rgbFilters.length == 3)
    assert(result.filterConfig.grayscaleFilters.length == 1)
  }
  
  test("parse complex command line") {
    val args = Array(
      "--image", "test.jpg",
      "--rotate", "90",
      "--scale", "0.25",
      "--brightness", "+10",
      "--table", "bourke",
      "--output-console",
      "--output-file", "output.txt"
    )
    val result = parser.parse(args)

    assert(result.imagePath.contains("test.jpg"))
    assert(result.filterConfig.rgbFilters.length == 2)
    assert(result.filterConfig.grayscaleFilters.length == 1)
    assert(result.tableName.contains("bourke"))
    assert(result.outputConsole)
    assert(result.outputFile.contains("output.txt"))
  }

  test("throws error when no image source") {
    val args = Array("--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("No image source specified"))
  }

  test("throws error when no output") {
    val args = Array("--image", "test.jpg")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("No output specified"))
  }

  test("throws error for multiple image sources") {
    val args = Array("--image", "test.jpg", "--image-random", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Cannot specify multiple image sources"))
  }

  test("throws error for multiple tables") {
    val args = Array("--image", "test.jpg", "--table", "bourke", "--custom-table", ".#@", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Cannot specify multiple table arguments"))
  }

  test("throws error for invalid rotation") {
    val args = Array("--image", "test.jpg", "--rotate", "45", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Rotation must be divisible by 90"))
  }

  test("throws error for invalid scale factor") {
    val args = Array("--image", "test.jpg", "--scale", "2.0", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Invalid scale factor"))
  }

  test("throws error for invalid font-aspect-ratio") {
    val args = Array("--image", "test.jpg", "--font-aspect-ratio", "x:y", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Invalid aspect ratio"))
  }

  test("throws error for invalid axis") {
    val args = Array("--image", "test.jpg", "--flip", "z", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Flip axis must be 'x' or 'y'"))
  }

  test("throws error for missing argument") {
    val args = Array("--image", "test.jpg", "--rotate", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Invalid rotation degrees"))
  }

  test("throws error for unknown argument") {
    val args = Array("--image", "test.jpg", "--unknown", "--output-console")

    val ex = intercept[IllegalArgumentException] {
      parser.parse(args)
    }
    assert(ex.getMessage.contains("Unknown argument"))
  }
}
