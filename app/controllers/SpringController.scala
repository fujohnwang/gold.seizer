package controllers

import org.springframework.stereotype.Component
import play.api.mvc.{Action, Controller}

@Component
class SpringController extends Controller {
  def index = Action {
    Ok("ok")
  }
}