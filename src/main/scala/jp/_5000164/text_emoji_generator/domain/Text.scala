package jp._5000164.text_emoji_generator.domain

object Text {
  def calculateFontSize(lines: List[String]): Int = {
    val side = 128
    val maxRow = lines.length
    side / maxRow
  }

  def calculatePosition(lines: List[String], align: Align): List[PrintChar] = {
    val side = 128
    val maxLength = lines.reduceLeft((a, b) => if (a.length > b.length) a else b).length
    val maxRow = lines.length
    val width = side / (if (maxLength > maxRow) maxLength else maxRow)
    val height = calculateFontSize(lines)

    val heightUnit = side / maxRow
    val heightUnitCenter = heightUnit / 2
    val widthUnit = side / maxLength
    val widthUnitCenter = widthUnit / 2

    var result: List[PrintChar] = List()

    if (align == Left) {
      var rowNumber = 1
      for (line <- lines) {
        var columnNumber = 1
        for (char <- line) {
          result = PrintChar(
            char.toString,
            widthUnit * columnNumber - widthUnitCenter,
            heightUnit * rowNumber - heightUnitCenter,
            width,
            height
          ) :: result
          columnNumber += 1
        }
        rowNumber += 1
      }
    } else {
      var rowNumber = 1
      for (line <- lines) {
        val lineWidth = widthUnit * line.length
        val margin = (side - lineWidth) / 2
        var columnNumber = 1
        for (char <- line) {
          result = PrintChar(
            char.toString,
            margin + widthUnit * columnNumber - widthUnitCenter,
            heightUnit * rowNumber - heightUnitCenter,
            width,
            height
          ) :: result
          columnNumber += 1
        }
        rowNumber += 1
      }
    }

    result.reverse
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
