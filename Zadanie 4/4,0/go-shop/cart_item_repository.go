package main

import "gorm.io/gorm"

type CartItemRepository struct {
	db *gorm.DB
}

func NewCartItemRepository(db *gorm.DB) *CartItemRepository {
	return &CartItemRepository{db: db}
}

func (r *CartItemRepository) Create(c *CartItem) error {
	return r.db.Create(c).Error
}

func (r *CartItemRepository) FindAll() ([]CartItem, error) {
	var cartItems []CartItem
	err := r.db.Preload("Product").Find(&cartItems).Error
	return cartItems, err
}

func (r *CartItemRepository) Update(c *CartItem) error {
	return r.db.Save(c).Error
}

func (r *CartItemRepository) Delete(id uint) error {
	return r.db.Delete(&CartItem{}, id).Error
}
