package main

type ProductService struct {
	repo *ProductRepository
}

func NewProductService(repo *ProductRepository) *ProductService {
	return &ProductService{repo: repo}
}

func (s *ProductService) CreateProduct(p *Product) error {
	return s.repo.Create(p)
}

func (s *ProductService) GetAllProducts() ([]Product, error) {
	return s.repo.FindAll()
}

func (s *ProductService) GetProductByID(id uint) (*Product, error) {
	return s.repo.FindByID(id)
}

func (s *ProductService) UpdateProduct(p *Product) error {
	return s.repo.Update(p)
}

func (s *ProductService) DeleteProduct(id uint) error {
	return s.repo.Delete(id)
}
