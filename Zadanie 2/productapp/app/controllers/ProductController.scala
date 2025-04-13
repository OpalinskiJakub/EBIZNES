package controllers

import play.api.mvc._
import javax.inject._
import models.Product
import play.api.libs.json._

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val productWrites: Writes[Product] = Json.writes[Product]

  def getAllProducts = Action {
    Ok(Json.toJson(Product.findAll()))
  }

  def getProductById(id: Long) = Action {
    Product.findById(id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound(Json.obj("error" -> s"Product with id $id not found"))
    }
  }
}
