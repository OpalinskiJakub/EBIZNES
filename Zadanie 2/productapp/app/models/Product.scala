package models

case class Product(id: Long, name: String, price: Double)

object Product {
  var products = List(
    Product(1, "Laptop", 3000.0),
    Product(2, "Smartphone", 1500.0),
    Product(3, "Tablet", 800.0)
  )

  def findAll(): List[Product] = products

  def findById(id: Long): Option[Product] = products.find(_.id == id)
}
