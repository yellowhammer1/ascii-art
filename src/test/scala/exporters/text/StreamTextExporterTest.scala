package exporters.text

import asciiArtApp.exporters.text.StreamTextExporter
import org.scalatest.funsuite.AnyFunSuite
import java.io.ByteArrayOutputStream

class StreamTextExporterTest extends AnyFunSuite {
  test("StreamTextExporter writes simple text to stream") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.output("Hello World")

    assert(stream.toString("UTF-8") == "Hello World")
  }

  test("StreamTextExporter writes multiline text") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.output("Line 1\nLine 2\nLine 3")

    assert(stream.toString("UTF-8") == "Line 1\nLine 2\nLine 3")
  }

  test("StreamTextExporter writes empty string") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.output("")

    assert(stream.toString("UTF-8") == "")
  }

  test("StreamTextExporter handles special characters") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    val specialChars = "!@#$%^&*()_+-=[]{}|;':\",./<>?"
    exporter.output(specialChars)

    assert(stream.toString("UTF-8") == specialChars)
  }

  test("StreamTextExporter handles large text") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    val largeText = "X" * 10000
    exporter.output(largeText)

    assert(stream.toString("UTF-8") == largeText)
  }

  test("StreamTextExporter writes multiple times") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.output("First")
    exporter.output("Second")

    assert(stream.toString("UTF-8") == "FirstSecond")
  }

  test("StreamTextExporter throws exception when writing to closed stream") {
    val stream = new ByteArrayOutputStream()
    val exporter = new StreamTextExporter(stream)

    exporter.close()

    assertThrows[Exception] {
      exporter.output("Should fail")
    }
  }
}
