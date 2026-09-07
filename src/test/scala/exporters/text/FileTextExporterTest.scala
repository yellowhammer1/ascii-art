package exporters.text

import asciiArtApp.exporters.text.FileTextExporter
import org.scalatest.funsuite.AnyFunSuite
import java.io.File
import java.nio.file.Files
import scala.io.Source

class FileTextExporterTest extends AnyFunSuite {
  test("FileTextExporter writes text to file") {
    val tempFile = File.createTempFile("test-output", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileTextExporter(tempFile)

    try {
      exporter.output("Hello World")
      exporter.close()

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content == "Hello World")
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("FileTextExporter writes empty string") {
    val tempFile = File.createTempFile("test-empty", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileTextExporter(tempFile)

    try {
      exporter.output("")
      exporter.close()

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content == "")
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }
  
  test("FileTextExporter overwrites existing file") {
    val tempFile = File.createTempFile("test-overwrite", ".txt")
    tempFile.deleteOnExit()

    Files.write(tempFile.toPath, "Old content".getBytes("UTF-8"))

    val exporter = new FileTextExporter(tempFile)

    try {
      exporter.output("New content")
      exporter.close()

      val source = Source.fromFile(tempFile)
      try {
        val content = source.mkString
        assert(content == "New content")
      } finally {
        source.close()
      }
    } finally {
      tempFile.delete()
    }
  }

  test("FileTextExporter creates file if it doesn't exist") {
    val testFile = new File(System.getProperty("java.io.tmpdir"), "new-test-file.txt")

    if (testFile.exists()) testFile.delete()

    val exporter = new FileTextExporter(testFile)

    try {
      exporter.output("New file content")
      exporter.close()

      assert(testFile.exists())

      val source = Source.fromFile(testFile)
      try {
        val content = source.mkString
        assert(content == "New file content")
      } finally {
        source.close()
      }
    } finally {
      testFile.delete()
    }
  }

  test("FileTextExporter throws exception when writing to closed stream") {
    val tempFile = File.createTempFile("test-closed", ".txt")
    tempFile.deleteOnExit()

    val exporter = new FileTextExporter(tempFile)
    exporter.close()

    try {
      assertThrows[Exception] {
        exporter.output("Should fail")
      }
    } finally {
      tempFile.delete()
    }
  }
}
