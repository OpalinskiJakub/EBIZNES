package models

case class Product(id: Long, name: String, price: Double)

object Product {
  private var products = List(
    Product(1, "Laptop", 3000.0),
    Product(2, "Smartphone", 1500.0),
    Product(3, "Tablet", 800.0)
  )

  def findAll(): List[Product] = products

  def findById(id: Long): Option[Product] = products.find(_.id == id)

  def add(product: Product): Unit = {
    products = products :+ product
  }

  def update(id: Long, updated: Product): Boolean = {
    findById(id) match {
      case Some(_) =>
        products = products.map(p => if (p.id == id) updated else p)
        true
      case None => false
    }
  }

  def delete(id: Long): Boolean = {
    val originalSize = products.size
    products = products.filterNot(_.id == id)
    products.size < originalSize
  }
}
