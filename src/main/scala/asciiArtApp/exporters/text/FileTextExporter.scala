package asciiArtApp.exporters.text

import java.io.{File, FileOutputStream}

class FileTextExporter(file: File)
  extends StreamTextExporter(new FileOutputStream(file))
{

}

