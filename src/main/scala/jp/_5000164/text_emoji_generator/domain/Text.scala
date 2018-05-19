package jp._5000164.text_emoji_generator.domain

object Text {
  val side = 128

  def calculateFontSize(lines: List[String]): Int = {
    val maxRow = lines.length
    side / maxRow
  }

  def calculatePosition(lines: List[String], align: Align): List[PrintChar] = {
    val maxLength = lines.reduceLeft((a, b) => if (a.length > b.length) a else b).length
    val maxRow = lines.length
    val width = side / (if (maxLength > maxRow) maxLength else maxRow)
    val height = calculateFontSize(lines)

    val heightUnit = side / maxRow
    val heightUnitCenter = heightUnit / 2
    val widthUnit = side / maxLength
    val widthUnitCenter = widthUnit / 2

    align match {
      case Left =>
        for (
          (line, rowNumber) <- lines.zipWithIndex;
          (char, columnNumber) <- line.zipWithIndex
        ) yield
          PrintChar(
            char.toString,
            widthUnit * (columnNumber + 1) - widthUnitCenter,
            heightUnit * (rowNumber + 1) - heightUnitCenter,
            width,
            height
          )
      case Center =>
        for (
          (line, rowNumber) <- lines.zipWithIndex;
          (char, columnNumber) <- line.zipWithIndex
        ) yield {
          val lineWidth = widthUnit * line.length
          val margin = (side - lineWidth) / 2
          PrintChar(
            char.toString,
            margin + widthUnit * (columnNumber + 1) - widthUnitCenter,
            heightUnit * (rowNumber + 1) - heightUnitCenter,
            width,
            height
          )
        }
    }
  }

  val colorList = List(
    ("Red", "F44336"),
    ("Pink", "E91E63"),
    ("Purple", "9C27B0"),
    ("Deep Purple", "673AB7"),
    ("Indigo", "3F51B5"),
    ("Blue", "2196F3"),
    ("Light Blue", "03A9F4"),
    ("Cyan", "00BCD4"),
    ("Teal", "009688"),
    ("Green", "4CAF50"),
    ("Light Green", "8BC34A"),
    ("Lime", "CDDC39"),
    ("Yellow", "FFEB3B"),
    ("Amber", "FFC107"),
    ("Orange", "FF9800"),
    ("Deep Orange", "FF5722"),
    ("Brown", "795548"),
    ("Grey", "9E9E9E"),
    ("Blue Grey", "607D8B"),
    ("Black", "000000")
  )
}

case class PrintChar(
                      content: String,
                      x: Double,
                      y: Double,
                      width: Double,
                      height: Double
                    )
