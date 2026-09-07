package asciiArtApp.exporters.text

class ConsoleTextExporter extends StreamTextExporter(System.out) {
  override def close(): Unit = {
    System.out.flush()
  }
}
