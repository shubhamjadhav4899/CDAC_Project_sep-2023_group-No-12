package in.medezee.service;

import in.medezee.exception.CartItemException;
import in.medezee.exception.UserException;
import in.medezee.modal.Cart;
import in.medezee.modal.CartItem;
import in.medezee.modal.Product;

public interface CartItemService {
	
	public CartItem createCartItem(CartItem cartItem);
	
	public CartItem updateCartItem(Long userId,Long id,CartItem cartItem)throws CartItemException,UserException;
	
	public CartItem isCartItemExist(Product product,Cart cart,String size,Long userId);
	
	public void removeCartItem(Long userId,Long cartItemId)throws CartItemException,UserException;
	
	public CartItem findCartItemById(Long cartId)throws CartItemException;
}
