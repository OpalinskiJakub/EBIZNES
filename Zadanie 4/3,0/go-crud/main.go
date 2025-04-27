package main

import (
	"github.com/labstack/echo/v4"
)

func main() {
	e := echo.New()
	e.POST("/products", CreateProduct)
	e.GET("/products", GetProducts)
	e.GET("/products/:id", GetProduct)
	e.PUT("/products/:id", UpdateProduct)
	e.DELETE("/products/:id", DeleteProduct)
	e.Logger.Fatal(e.Start(":8080"))
}
