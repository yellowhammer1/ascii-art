package asciiArtApp.ui.parsers

import asciiArtApp.filters.{Axis, BrightnessFilter, FlipFilter, FontAspectRatioFilter, InvertFilter, RotateFilter, ScaleFilter}
import asciiArtApp.ui.configs.FilterConfig

import scala.annotation.tailrec

/**
 * Parses command line arguments into a configuration object.
 * Validates that required arguments are present and filters are properly ordered.
 */
class ArgumentParser {
  /**
   * Parses command line arguments.
   *
   * @param arguments the command line arguments array
   * @return the parsed configuration
   * @throws IllegalArgumentException if arguments are invalid or missing
   */
  def parse(arguments: Array[String]): ParsedArguments = {
    @tailrec
    def parseRec(args: List[String], config: ParsedArguments): ParsedArguments = {
      args match {
        case Nil => config

        case "--image" :: path :: rest =>
          if (config.imagePath.isDefined || config.imageRandom) {
            throw new IllegalArgumentException("Cannot specify multiple image sources")
          }
          parseRec(rest, config.copy(imagePath = Some(path)))

        case "--image-random" :: rest =>
          if (config.imagePath.isDefined || config.imageRandom) {
            throw new IllegalArgumentException("Cannot specify multiple image sources")
          }
          parseRec(rest, config.copy(imageRandom = true))

        case "--table" :: name :: rest =>
          if (config.tableName.isDefined || config.customTable.isDefined) {
            throw new IllegalArgumentException("Cannot specify multiple table arguments")
          }
          parseRec(rest, config.copy(tableName = Some(name)))

        case "--custom-table" :: chars :: rest =>
          if (config.tableName.isDefined || config.customTable.isDefined) {
            throw new IllegalArgumentException("Cannot specify multiple table arguments")
          }
          parseRec(rest, config.copy(customTable = Some(chars)))

        case "--output-console" :: rest =>
          parseRec(rest, config.copy(outputConsole = true))

        case "--output-file" :: path :: rest =>
          if (config.outputFile.isDefined) {
            throw new IllegalArgumentException("Cannot specify --output-file multiple times")
          }
          parseRec(rest, config.copy(outputFile = Some(path)))

        case "--rotate" :: degrees :: rest =>
          val deg = parseRotation(degrees)
          val filter = new RotateFilter(deg)
          val updatedFilters = config.filterConfig.copy(
            rgbFilters = config.filterConfig.rgbFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--scale" :: factor :: rest =>
          val f = parseScale(factor)
          val filter = new ScaleFilter(f)
          val updatedFilters = config.filterConfig.copy(
            rgbFilters = config.filterConfig.rgbFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--flip" :: axis :: rest =>
          val ax = parseAxis(axis)
          val filter = new FlipFilter(ax)
          val updatedFilters = config.filterConfig.copy(
            rgbFilters = config.filterConfig.rgbFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--font-aspect-ratio" :: ratio :: rest =>
          val (x, y) = parseAspectRatio(ratio)
          val filter = new FontAspectRatioFilter(x, y)
          val updatedFilters = config.filterConfig.copy(
            rgbFilters = config.filterConfig.rgbFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--invert" :: rest =>
          val filter = new InvertFilter()
          val updatedFilters = config.filterConfig.copy(
            grayscaleFilters = config.filterConfig.grayscaleFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--brightness" :: value :: rest =>
          val v = parseBrightness(value)
          val filter = new BrightnessFilter(v)
          val updatedFilters = config.filterConfig.copy(
            grayscaleFilters = config.filterConfig.grayscaleFilters :+ filter
          )
          parseRec(rest, config.copy(filterConfig = updatedFilters))

        case "--image" :: Nil | "--table" :: Nil | "--custom-table" :: Nil |
             "--output-file" :: Nil | "--rotate" :: Nil | "--scale" :: Nil |
             "--flip" :: Nil | "--brightness" :: Nil | "--font-aspect-ratio" :: Nil =>
          throw new IllegalArgumentException(s"${args.head} requires an argument")

        case unknown :: _ =>
          throw new IllegalArgumentException(s"Unknown argument: $unknown")
      }
    }

    val config = parseRec(arguments.toList, ParsedArguments())
    validate(config)
    config
  }

  private def validate(config: ParsedArguments): Unit = {
    if (!config.imageRandom && config.imagePath.isEmpty) {
      throw new IllegalArgumentException(
        "No image source specified. Use --image <path> or --image-random"
      )
    }

    if (!config.outputConsole && config.outputFile.isEmpty) {
      throw new IllegalArgumentException(
        "No output specified. Use --output-console or --output-file <path>"
      )
    }
  }

  /**
   * Parses rotation degrees from string.
   * Accepts positive/negative values with optional '+' prefix.
   * Rotation must be divisible by 90.
   *
   * @param degrees the degrees string (e.g., "90", "+180", "-90")
   * @return the parsed degrees
   * @throws IllegalArgumentException if invalid or not divisible by 90
   */
  private def parseRotation(degrees: String): Int = {
    try {
      val deg = if (degrees.startsWith("+")) degrees.substring(1).toInt else degrees.toInt
      if (deg % 90 != 0) {
        throw new IllegalArgumentException(s"Rotation must be divisible by 90, got: $deg")
      }
      deg
    } catch {
      case _: NumberFormatException =>
        throw new IllegalArgumentException(s"Invalid rotation degrees: $degrees")
    }
  }

  /**
   * Parses scale factor from string.
   * Only 0.25, 1.0, and 4.0 are supported.
   *
   * @param scaleFactor the scale factor string
   * @return the parsed scale factor
   * @throws IllegalArgumentException if not a supported value
   */
  private def parseScale(scaleFactor: String): Double = {
    try {
      val factor = scaleFactor.toDouble
      if (factor != 0.25 && factor != 1.0 && factor != 4.0) {
        throw new IllegalArgumentException(s"Invalid scale factor: $factor. Supported values: 0.25, 1, 4")
      }
      factor
    } catch {
      case _: NumberFormatException =>
        throw new IllegalArgumentException(s"Invalid scale factor: $scaleFactor")
    }
  }

  private def parseAxis(axis: String): Axis = {
    val ax = axis.toLowerCase
    ax match {
      case "x" => Axis.x
      case "y" => Axis.y
      case _ => throw new IllegalArgumentException(s"Flip axis must be 'x' or 'y', got: $axis")
    }
  }

  private def parseBrightness(value: String): Int = {
    try {
      if (value.startsWith("+")) value.substring(1).toInt else value.toInt
    } catch {
      case _: NumberFormatException =>
        throw new IllegalArgumentException(s"Invalid brightness value: $value")
    }
  }
  
  /**
   * Parses aspect ratio from "x:y" format.
   *
   * @param ratio the ratio string (e.g., "1:2", "3:4")
   * @return tuple of (x, y)
   * @throws IllegalArgumentException if format is invalid
   */
  private def parseAspectRatio(ratio: String): (Int, Int) = {
    ratio.split(":") match {
      case Array(x, y) =>
        try {
          (x.toInt, y.toInt)
        } catch {
          case _: NumberFormatException =>
            throw new IllegalArgumentException(s"Invalid aspect ratio: $ratio")
        }
      case _ =>
        throw new IllegalArgumentException(s"Aspect ratio must be in format x:y, where x,y are numbers, got: $ratio")
    }
  }
}
