package controllers

import play.api.mvc._

class Application extends Controller {

  def main() = Action {
    Ok(views.html.datatable())
  }

}