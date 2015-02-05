package controllers

import play._
import play.api.mvc._


object Application extends Controller {

  def index = Action {
    Ok(views.html.pi())
  }

  //  def qrcode = Action {
  //    implicit req =>
  //      req.getQueryString("url") match {
  //        case None => Results.BadRequest
  //        case Some(url) => {
  //          val bitMatrix = new QRCodeWriter().encode(new URI(url).toASCIIString, BarcodeFormat.QR_CODE, 175, 175)
  //          val outputStream = new ByteArrayOutputStream()
  //          MatrixToImageWriter.writeToStream(bitMatrix, "png", outputStream)
  //          Result(header = ResponseHeader(200, Map(CONTENT_TYPE -> "image/png")), body = Enumerator(outputStream.toByteArray))
  //        }
  //      }
  //  }
}