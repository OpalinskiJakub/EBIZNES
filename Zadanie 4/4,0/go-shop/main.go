package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func main() {
	db, err := gorm.Open(sqlite.Open("shop.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}

	db.AutoMigrate(&Product{}, &CartItem{})

	productRepo := NewProductRepository(db)
	productService := NewProductService(productRepo)
	productController := NewProductController(productService)

	cartItemRepo := NewCartItemRepository(db)
	cartItemService := NewCartItemService(cartItemRepo)
	cartItemController := NewCartItemController(cartItemService)

	e := echo.New()

	e.POST("/products", productController.CreateProduct)
	e.GET("/products", productController.GetProducts)
	e.GET("/products/:id", productController.GetProduct)
	e.PUT("/products/:id", productController.UpdateProduct)
	e.DELETE("/products/:id", productController.DeleteProduct)

	e.POST("/cart-items", cartItemController.CreateCartItem)
	e.GET("/cart-items", cartItemController.GetCartItems)
	e.PUT("/cart-items/:id", cartItemController.UpdateCartItem)
	e.DELETE("/cart-items/:id", cartItemController.DeleteCartItem)

	e.Logger.Fatal(e.Start(":8083"))
}
