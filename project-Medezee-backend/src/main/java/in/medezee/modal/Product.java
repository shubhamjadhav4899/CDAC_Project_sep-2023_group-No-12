package in.medezee.modal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	private String description;
	
	private int price;
	
	@Column(name="dicounted_price")
	private int discountedPrice;
	
	@Column(name="discount_present")
	private int discountPresent;
	
	private int quantity;
	
	private String brand;
	
	private String color;
	
	@Embedded
	@ElementCollection
	private Set<Size> sizes=new HashSet<>();
	
	@Column(name="image_url")
	private String imageUrl;
	
	@OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval = true)
	private List<Rating> ratings=new ArrayList<>();
	
	@OneToMany(mappedBy = "product",cascade=CascadeType.ALL,orphanRemoval = true)
	private List<Review> reviews=new ArrayList<>();
	
	@Column(name="num_rating")
	private int numRating;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	private LocalDateTime cretedAt;
	
	
	
	
}
