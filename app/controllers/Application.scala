package controllers

import play._
import play.api.mvc._


object Application extends Controller {

  def main = Action {
    Ok(views.html.main())
  }

}