package jp._5000164.text_emoji_generator.domain

object Text {
  /** 配置できる枠の一辺の長さ */
  val side = 128

  def calculatePosition(text: String, align: Align): (Seq[String], Seq[Seq[CharSize]], Seq[Seq[CharPosition]]) = {
    val lines = text.split("\n").toSeq
    val charSizeMatrix = calculateCharSize(lines)
    val charPositionMatrix = calculateCharPosition(charSizeMatrix, align)
    (lines, charSizeMatrix, charPositionMatrix)
  }

  /**
    * 文字の大きさを計算する。
    *
    * @param lines 1 行ごとに区切った内容
    * @return 文字ごとの大きさのマトリックス
    */
  private def calculateCharSize(lines: Seq[String]): Seq[Seq[CharSize]] = {
    val unitHeight = calculateUnitHeight(lines)
    val unitWidth = calculateUnitWidth(lines, unitHeight)

    for (line <- lines) yield
      for (_ <- line) yield {
        CharSize(unitWidth, unitHeight)
      }
  }

  /**
    * 基本となる文字の高さを計算する。
    *
    * @param lines 1 行ごとに区切った内容
    * @return 基本となる文字の高さ
    */
  private def calculateUnitHeight(lines: Seq[String]): Double = side / lines.length

  /**
    * 基本となる文字の幅を計算する。
    *
    * @param lines      1 行ごとに区切った内容
    * @param unitHeight 基本となる文字の高さ
    * @return 基本となる文字の幅
    */
  private def calculateUnitWidth(lines: Seq[String], unitHeight: Double): Double = {
    val maxLength = lines.map(_.length).max
    val provisionalUnitWidth = side / maxLength

    // 文字の幅は文字の高さを超えて指定することはできない
    if (provisionalUnitWidth <= unitHeight) provisionalUnitWidth else unitHeight
  }

  /**
    * 文字の位置を計算する。
    *
    * @param charSizeMatrix 文字ごとの大きさのマトリックス
    * @param align          文字の位置揃え
    * @return 文字ごとの位置のマトリックス
    */
  private def calculateCharPosition(charSizeMatrix: Seq[Seq[CharSize]], align: Align): Seq[Seq[CharPosition]] = {
    var endX = 0.0
    var endY = 0.0

    for (charSizeList <- charSizeMatrix) yield {
      endX = 0.0
      val height = charSizeList.head.height
      endY = endY + height
      val margin = calculateMargin(align, charSizeList)
      for (charSize <- charSizeList) yield {
        val width = charSize.width
        endX = endX + width
        CharPosition(
          x = margin + endX - (width / 2),
          y = endY - (height / 2)
        )
      }
    }
  }

  /**
    * 行の開始位置までのマージンを計算する。
    *
    * @param align        文字の位置揃え
    * @param charSizeList 文字ごとの大きさのリスト
    * @return 行の開始位置までのマージン
    */
  private def calculateMargin(align: Align, charSizeList: Seq[CharSize]): Double = align match {
    case Left => 0
    case Center =>
      val totalWidth = charSizeList.map(_.width).sum
      (side - totalWidth) / 2
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

case class CharSize(
                     width: Double,
                     height: Double
                   )

case class CharPosition(
                         x: Double,
                         y: Double
                       )
