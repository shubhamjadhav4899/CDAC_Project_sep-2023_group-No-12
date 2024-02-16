package in.medezee.request;

import java.util.HashSet;
import java.util.Set;

import in.medezee.modal.Size;
import lombok.Data;

@Data
public class CreateProductRequest {

	private String titile;
	private String description;
	private int price;
	private int discountedPrice;
	private int discountPresent;
	private int quantity;
	private String brand;
	private String color;
	private Set<Size> size=new HashSet<>();
	private String imageUrl;
	private String topLevelCategory;
	private String secondLevelCategory;
	private String thirdLevelCategory;
	
}
