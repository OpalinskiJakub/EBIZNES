package main

type CartItemService struct {
	repo *CartItemRepository
}

func NewCartItemService(repo *CartItemRepository) *CartItemService {
	return &CartItemService{repo: repo}
}

func (s *CartItemService) CreateCartItem(c *CartItem) error {
	return s.repo.Create(c)
}

func (s *CartItemService) GetAllCartItems() ([]CartItem, error) {
	return s.repo.FindAll()
}

func (s *CartItemService) UpdateCartItem(c *CartItem) error {
	return s.repo.Update(c)
}

func (s *CartItemService) DeleteCartItem(id uint) error {
	return s.repo.Delete(id)
}
