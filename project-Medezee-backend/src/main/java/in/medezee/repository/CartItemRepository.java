package in.medezee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.medezee.modal.Cart;
import in.medezee.modal.CartItem;
import in.medezee.modal.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	
	@Query("SELECT ci from CartItem ci where ci.cart=:cart And ci.product=:product And ci.size =:size And ci.userId=:userId")
	public CartItem isCartItemExist(@Param("cart") Cart cart,@Param("product")Product product
			,@Param("size")String size,@Param("userId")Long userId);
}
