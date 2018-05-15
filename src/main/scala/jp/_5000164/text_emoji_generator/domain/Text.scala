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
    val maxWidth = side / (if (maxLength > maxRow) maxLength else maxRow)

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
            maxWidth
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
            maxWidth
          ) :: result
          columnNumber += 1
        }
        rowNumber += 1
      }
    }

    result.reverse
  }
}

case class PrintChar(
                      content: String,
                      x: Double,
                      y: Double,
                      maxWidth: Double
                    )
