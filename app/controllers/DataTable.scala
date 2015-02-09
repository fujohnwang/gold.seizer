package controllers

import org.springframework.stereotype.Component
import play.api.mvc.{Action, Controller}
import vo.DataTableView
import vo.JsonTest.Row


/**
 * <h1>DataTable说明</h1>
 *
 * <h2>请求参数说明</h2>
 * <ul>
 * <li>draw参数是客户端data table渲染的次数， 客户端发起数据请求的时候会一起传递上来， 只要每次将传递的数值再原样传回给客户端即可；</li>
 * <li>start参数表明当前显示表格窗口的起始记录index， 比如结果集的第十行作为开始， 或者第20行作为开始， 等等；</li>
 * <li>length表示当前表格窗口每次显示的记录数， 相当于pageSize；</li>
 * </ul>
 *
 * 元组(start, start+length)形成当前请求页面的记录窗口界定标志。
 * <h2>返回结果结构说明</h2>
 * <p>返回json格式的结果数据， 类似于：</p>
 * <pre>
 * {
 * "draw": 2,
    "recordsTotal": 57,
    "recordsFiltered": 57,
    "data": [
    [
      "Garrett",
      "Winters",
      "Accountant",
      "Tokyo",
      "25th Jul 11",
      "$170,750"
    ],
    ...
    ]
 * }
 * </pre>
 * <p>
 * 每行数据以json数组的形式返回， 所有行也是以json数组的形式返回，最终放在data字段下面， draw字段在上面参数说明中已经讲了， 其它几个字段也很容易理解，如果不允许客户端过滤，直接将recordsTotal和recordsFiltered设置为同样地值即可， 即data store中一共有多少复合展示条件的数据。
 * </p>
 *
 */
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