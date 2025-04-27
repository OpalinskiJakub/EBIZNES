package main

import (
	"net/http"
	"strconv"

	"github.com/labstack/echo/v4"
)

type CartItemController struct {
	service *CartItemService
}

func NewCartItemController(service *CartItemService) *CartItemController {
	return &CartItemController{service: service}
}

func (cc *CartItemController) CreateCartItem(c echo.Context) error {
	cartItem := new(CartItem)
	if err := c.Bind(cartItem); err != nil {
		return err
	}
	if err := cc.service.CreateCartItem(cartItem); err != nil {
		return err
	}
	return c.JSON(http.StatusCreated, cartItem)
}

func (cc *CartItemController) GetCartItems(c echo.Context) error {
	cartItems, err := cc.service.GetAllCartItems()
	if err != nil {
		return err
	}
	return c.JSON(http.StatusOK, cartItems)
}

func (cc *CartItemController) UpdateCartItem(c echo.Context) error {
	id, _ := strconv.Atoi(c.Param("id"))
	cartItem := new(CartItem)
	if err := c.Bind(cartItem); err != nil {
		return err
	}
	cartItem.ID = uint(id)
	if err := cc.service.UpdateCartItem(cartItem); err != nil {
		return err
	}
	return c.JSON(http.StatusOK, cartItem)
}

func (cc *CartItemController) DeleteCartItem(c echo.Context) error {
	id, _ := strconv.Atoi(c.Param("id"))
	if err := cc.service.DeleteCartItem(uint(id)); err != nil {
		return err
	}
	return c.NoContent(http.StatusNoContent)
}
