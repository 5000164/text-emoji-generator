package jp._5000164.text_emoji_generator.domain

import jp._5000164.text_emoji_generator.interfaces.PrintChar

object Text {
  val side = 128

  def calculateFontSize(lines: List[String]): Int = {
    val maxRow = lines.length
    side / maxRow
  }

  def calculatePosition(lines: List[String], align: Align): List[PrintChar] = {
    val maxLength = lines.map(_.length).max
    val maxRow = lines.length
    val width = side / (if (maxLength > maxRow) maxLength else maxRow)
    val height = calculateFontSize(lines)

    val heightUnit = side / maxRow
    val heightUnitCenter = heightUnit / 2
    val widthUnit = side / maxLength
    val widthUnitCenter = widthUnit / 2

    for (
      (line, rowNumber) <- lines.zipWithIndex;
      (char, columnNumber) <- line.zipWithIndex
    ) yield {
      val margin = align match {
        case Left => 0
        case Center =>
          val lineWidth = widthUnit * line.length
          (side - lineWidth) / 2
      }
      val x = margin + widthUnit * (columnNumber + 1) - widthUnitCenter
      val y = heightUnit * (rowNumber + 1) - heightUnitCenter
      PrintChar(char.toString, x, y, width, height)
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
