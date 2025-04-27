package main

import (
	"net/http"
	"strconv"

	"github.com/labstack/echo/v4"
)

type ProductController struct {
	service *ProductService
}

func NewProductController(service *ProductService) *ProductController {
	return &ProductController{service: service}
}

func (pc *ProductController) CreateProduct(c echo.Context) error {
	p := new(Product)
	if err := c.Bind(p); err != nil {
		return err
	}
	if err := pc.service.CreateProduct(p); err != nil {
		return err
	}
	return c.JSON(http.StatusCreated, p)
}

func (pc *ProductController) GetProducts(c echo.Context) error {
	products, err := pc.service.GetAllProducts()
	if err != nil {
		return err
	}
	return c.JSON(http.StatusOK, products)
}

func (pc *ProductController) GetProduct(c echo.Context) error {
	id, _ := strconv.Atoi(c.Param("id"))
	product, err := pc.service.GetProductByID(uint(id))
	if err != nil {
		return c.JSON(http.StatusNotFound, echo.Map{"message": "Product not found"})
	}
	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) UpdateProduct(c echo.Context) error {
	id, _ := strconv.Atoi(c.Param("id"))
	product, err := pc.service.GetProductByID(uint(id))
	if err != nil {
		return c.JSON(http.StatusNotFound, echo.Map{"message": "Product not found"})
	}
	u := new(Product)
	if err := c.Bind(u); err != nil {
		return err
	}
	product.Name = u.Name
	product.Price = u.Price
	if err := pc.service.UpdateProduct(product); err != nil {
		return err
	}
	return c.JSON(http.StatusOK, product)
}

func (pc *ProductController) DeleteProduct(c echo.Context) error {
	id, _ := strconv.Atoi(c.Param("id"))
	if err := pc.service.DeleteProduct(uint(id)); err != nil {
		return err
	}
	return c.NoContent(http.StatusNoContent)
}
