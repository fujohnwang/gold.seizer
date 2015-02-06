package controllers

import org.springframework.stereotype
import play.api.mvc.{Action, Controller}

@stereotype.Controller
class Forex extends Controller {
  def index = Action{
    Ok("")
  }
}