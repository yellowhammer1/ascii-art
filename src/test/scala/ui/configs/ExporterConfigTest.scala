package ui.configs

import asciiArtApp.ui.configs.ExporterConfig
import org.scalatest.funsuite.AnyFunSuite
import java.io.{File, ByteArrayOutputStream}
import scala.io.Source

class ExporterConfigTest extends AnyFunSuite {
  test("exportResults exports to console when outputConsole is true") {
    val originalOut = System.out
    val outCapture = new ByteArrayOutputStream()

    try {
      System.setOut(new java.io.PrintStream(outCapture))
      ExporterConfig.exportResults("Test ASCII", outputConsole = true, outputFile = None)
    } finally {
      System.setOut(originalOut)
    }

    val output = outCapture.toString("UTF-8")
    assert(output == "Test ASCII")
  }

  test("exportResults does not export to console when outputConsole is false") {
    val outCapture = new ByteArrayOutputStream()
    Console.withOut(outCapture) {
      ExporterConfig.exportResults("Test ASCII", outputConsole = false, outputFile = None)
    }

    val output = outCapture.toString("UTF-8")
    assert(output.isEmpty)
  }

  test("exportResults exports to file when outputFile is Some") {
    val tempFile = File.createTempFile("test-export", ".txt")
    tempFile.deleteOnExit()

    try {
      ExporterConfig.exportResults("Test ASCII", outputConsole = false, outputFile = Some(tempFile.getPath))

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content == "Test ASCII")
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("exportResults does not export to file when outputFile is None") {
    ExporterConfig.exportResults("Test ASCII", outputConsole = false, outputFile = None)

    val noneFile = new File("None")
    assert(!noneFile.exists())
  }

  test("exportResults exports to both console and file") {
    val tempFile = File.createTempFile("test-both", ".txt")
    tempFile.deleteOnExit()

    val originalOut = System.out
    val outCapture = new ByteArrayOutputStream()

    try {
      System.setOut(new java.io.PrintStream(outCapture))
      ExporterConfig.exportResults("Test ASCII", outputConsole = true, outputFile = Some(tempFile.getPath))
    } finally {
      System.setOut(originalOut)
    }

    try {
      val consoleOutput = outCapture.toString("UTF-8")
      assert(consoleOutput == "Test ASCII")

      val source = Source.fromFile(tempFile)
      try {
        val fileContent = source.mkString
        assert(fileContent == "Test ASCII")
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("exportResults handles multiline text") {
    val tempFile = File.createTempFile("test-multiline", ".txt")
    tempFile.deleteOnExit()

    val multilineText = "Line 1\nLine 2\nLine 3"

    try {
      ExporterConfig.exportResults(multilineText, outputConsole = false, outputFile = Some(tempFile.getPath))

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content == multilineText)
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("exportResults handles empty string") {
    val tempFile = File.createTempFile("test-empty", ".txt")
    tempFile.deleteOnExit()

    try {
      ExporterConfig.exportResults("", outputConsole = false, outputFile = Some(tempFile.getPath))

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content.isEmpty)
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("exportResults throws exception for invalid file path") {
    val invalidPath = "/nonexistent/directory/test.txt"

    intercept[Exception] {
      ExporterConfig.exportResults("Test", outputConsole = false, outputFile = Some(invalidPath))
    }
  }
}
