package main

import (
	"gorm.io/gorm"
)

type ProductService struct {
	db *gorm.DB
}

func NewProductService(db *gorm.DB) *ProductService {
	return &ProductService{db: db}
}

func (s *ProductService) CreateProduct(p *Product) error {
	return s.db.Create(p).Error
}

func (s *ProductService) GetAllProducts() ([]Product, error) {
	var products []Product
	err := s.db.Find(&products).Error
	return products, err
}

func (s *ProductService) GetProductByID(id uint) (*Product, error) {
	var product Product
	err := s.db.First(&product, id).Error
	return &product, err
}

func (s *ProductService) UpdateProduct(product *Product) error {
	return s.db.Save(product).Error
}

func (s *ProductService) DeleteProduct(id uint) error {
	return s.db.Delete(&Product{}, id).Error
}
