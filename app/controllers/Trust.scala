package controllers

import org.springframework.stereotype
import play.api.mvc.{Action, Controller}

@stereotype.Controller
class Trust extends Controller {
  def index = Action{
    Ok("")
  }
}