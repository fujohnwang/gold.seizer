package controllers

import org.springframework.stereotype.Component
import play.api.mvc.{Action, Controller}
import vo.DataTableView
import vo.JsonTest.Row

@Component
class DataTableController extends Controller {
  def list() = Action { implicit request =>
    //    val row1 = new Row("darren", 35)

    val draw: Int = request.getQueryString("draw") match {
      case Some(d) => d.toInt
      case None => 1
    }

    val start: Int = request.getQueryString("start") match {
      case Some(page) => page.toInt
      case None => 0
    }

    val pageSize = request.getQueryString("length").get.toInt

    //    val query = request.getQueryString("search[value]")
    //    println(query.get)

    val rows = (0 until 100).map(_ => new Row("darren", 35))
    val dt = DataTableView(draw, rows.size, rows.slice(start, start + pageSize))
    Ok(dt.asJson())
  }
}