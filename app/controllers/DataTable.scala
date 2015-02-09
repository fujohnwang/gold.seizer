package controllers

import org.springframework.stereotype.Component
import play.api.mvc.{Action, Controller}
import vo.DataTableView
import vo.JsonTest.Row

@Component
class DataTableController extends Controller {
  def list() = Action { implicit request =>
    val row1 = new Row("darren", 35)
    val row2 = new Row("darren", 35)
    val dt = DataTableView(1, 1, Seq(row1, row2))
    Ok(dt.asJson())
  }
}