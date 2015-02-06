package controllers

import org.springframework.stereotype
import play.api.mvc.{Action, Controller}
import play.twirl.api.Html

@stereotype.Controller
class Bond extends Controller {
  def index = Action{
    Ok(views.html.layout("")(Html("ok")))
  }
}