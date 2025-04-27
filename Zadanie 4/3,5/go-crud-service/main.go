package main

import (
	"github.com/labstack/echo/v4"
	"gorm.io/driver/sqlite"
	"gorm.io/gorm"
)

func main() {
	db, err := gorm.Open(sqlite.Open("products.db"), &gorm.Config{})
	if err != nil {
		panic("failed to connect database")
	}

	db.AutoMigrate(&Product{})

	productService := NewProductService(db)
	productController := NewProductController(productService)

	e := echo.New()

	e.POST("/products", productController.CreateProduct)
	e.GET("/products", productController.GetProducts)
	e.GET("/products/:id", productController.GetProduct)
	e.PUT("/products/:id", productController.UpdateProduct)
	e.DELETE("/products/:id", productController.DeleteProduct)

	e.Logger.Fatal(e.Start(":8082"))
}
