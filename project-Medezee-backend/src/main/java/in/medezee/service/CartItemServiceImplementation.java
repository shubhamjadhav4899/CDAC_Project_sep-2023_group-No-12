package in.medezee.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import in.medezee.exception.CartItemException;
import in.medezee.exception.UserException;
import in.medezee.modal.Cart;
import in.medezee.modal.CartItem;
import in.medezee.modal.Product;
import in.medezee.modal.User;
import in.medezee.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartItemServiceImplementation implements CartItemService {

	private final CartItemRepository cartItemRepository;
	
	private final UserService userService;
	
//	private final CartRepository cartRepository;
	
	@Override
	public CartItem createCartItem(CartItem cartItem) {
		cartItem.setQunatity(1);
		cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQunatity());
		cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQunatity());
		
		
		CartItem createdCartItem=cartItemRepository.save(cartItem);
		
		return createdCartItem;
	}

	@Override
	public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
		CartItem item=findCartItemById(id);
		User user=userService.findUserById(item.getUserId());
		
		if(user.getId().equals(userId)) {
			item.setQunatity(cartItem.getQunatity());
			item.setPrice(item.getQunatity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQunatity());
		}
		
		return cartItemRepository.save(item);
	}

	@Override
	public CartItem isCartItemExist(Product product, Cart cart, String size, Long userId) {
		CartItem cartItem=cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItem cartItem=findCartItemById(userId);
		User user=userService.findUserById(cartItem.getUserId());
		
		User reqUser=userService.findUserById(userId);
		
		if(user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}
		else {
			throw new UserException("You can't remove another user Item");
		}
	}

	@Override
	public CartItem findCartItemById(Long cartId) throws CartItemException {
		
		Optional<CartItem> opt=cartItemRepository.findById(cartId);
		
		if(opt.isPresent()) {
			return opt.get();
		}
		throw new CartItemException("cartItem not found with id:"+cartId);
	}

}
