package vo

import play.api.libs.json.{JsValue, Json}


trait JsonView {
  def asJson(): JsValue
}

case class DataTableView[T <: JsonView](currentPage: Int, total: Long, rows: Seq[T]) {
  def asJson() = {
    Json.obj(
      "draw" -> currentPage,
      "recordsTotal" -> total,
      "recordsFiltered" -> total,
      "data" -> rows.map(_.asJson())
    )
  }
}


object JsonTest {

  case class Row(name: String, age: Int) extends JsonView {
    override def asJson() = Json.arr(name, age.toString)
  }

  def main(args: Array[String]) {
    val row1 = new Row("darren", 35)
    val row2 = new Row("darren", 35)
    val dt = DataTableView(1, 1, Seq(row1, row2))
    println(dt.asJson())
  }
}