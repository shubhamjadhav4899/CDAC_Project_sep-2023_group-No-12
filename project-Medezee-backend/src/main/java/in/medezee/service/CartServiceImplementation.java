package in.medezee.service;

import org.springframework.stereotype.Service;

import in.medezee.exception.ProductException;
import in.medezee.modal.Cart;
import in.medezee.modal.CartItem;
import in.medezee.modal.Product;
import in.medezee.modal.User;
import in.medezee.repository.CartRepository;
import in.medezee.request.AddItemRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImplementation implements CartService {

	private final CartRepository cartRepository;
	
	private final CartItemService cartItemService;	
	
	private final ProductService productService;
	@Override
	public Cart createCart(User user) {
		Cart cart=new Cart();
		cart.setUser(user);
		
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		
		Cart cart=cartRepository.findByUserId(userId);
		
		Product product=productService.findProductById(req.getProductId());
		
		CartItem isPresent=cartItemService.isCartItemExist(product, cart, req.getSize(), userId);
		
		if(isPresent==null) {
			CartItem cartItem=new CartItem();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQunatity(req.getQuantity());
			cartItem.setUserId(userId);
			
			int price=req.getQuantity()*product.getDiscountedPrice();
			
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItem createdCartItem=cartItemService.createCartItem(cartItem);
			cart.getCartItem().add(createdCartItem);
		}
		
		return "Item Added To Cart";
		
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart=cartRepository.findByUserId(userId);
		int totalPrice=0;
		int totalDiscountedPrice=0;
		int totalItem=0;
		
		for(CartItem cartItem:cart.getCartItem()) {
			totalPrice=totalPrice+cartItem.getPrice();
			
			totalDiscountedPrice=totalDiscountedPrice+cartItem.getDiscountedPrice();
			
			totalItem=totalItem+cartItem.getQunatity();
		}
		
		cart.setTotalDiscountPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItem);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart);
	}

}
