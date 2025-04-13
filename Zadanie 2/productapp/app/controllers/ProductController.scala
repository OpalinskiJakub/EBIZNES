package controllers

import play.api.mvc._
import javax.inject._
import models.Product
import play.api.libs.json._

@Singleton
class ProductController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val productFormat: OFormat[Product] = Json.format[Product]

  def getAllProducts = Action {
    Ok(Json.toJson(Product.findAll()))
  }

  def getProductById(id: Long) = Action {
    Product.findById(id) match {
      case Some(product) => Ok(Json.toJson(product))
      case None => NotFound(Json.obj("error" -> s"Product with id $id not found"))
    }
  }

  def addProduct: Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid product format")),
      product => {
        Product.add(product)
        Created(Json.toJson(product))
      }
    )
  }

  def updateProduct(id: Long): Action[JsValue] = Action(parse.json) { request =>
    request.body.validate[Product].fold(
      errors => BadRequest(Json.obj("error" -> "Invalid product format")),
      updatedProduct => {
        if (Product.update(id, updatedProduct))
          Ok(Json.toJson(updatedProduct))
        else
          NotFound(Json.obj("error" -> s"Product with id $id not found"))
      }
    )
  }


  def deleteProduct(id: Long) = Action {
    if (Product.delete(id))
      NoContent
    else
      NotFound(Json.obj("error" -> s"Product with id $id not found"))
  }
}
