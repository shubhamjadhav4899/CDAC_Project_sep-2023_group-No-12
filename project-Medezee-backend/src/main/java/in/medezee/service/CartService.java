package in.medezee.service;

import in.medezee.exception.ProductException;
import in.medezee.modal.Cart;
import in.medezee.modal.User;
import in.medezee.request.AddItemRequest;

public interface CartService {
	public Cart createCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);
}
