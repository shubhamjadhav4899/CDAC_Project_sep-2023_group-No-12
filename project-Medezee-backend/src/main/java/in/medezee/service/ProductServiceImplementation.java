package in.medezee.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.medezee.exception.ProductException;
import in.medezee.modal.Category;
import in.medezee.modal.Product;
import in.medezee.repository.CategoryRepository;
import in.medezee.repository.ProductRepository;
import in.medezee.request.CreateProductRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImplementation implements ProductService{

	private final ProductRepository productRepository;
	
	
//	private final UserService userService;
	
	private final CategoryRepository categoryRepository;
	@Override
	public Product createProduct(CreateProductRequest req) {
		Category topLevel=categoryRepository.findByName(req.getTopLevelCategory());
		if(topLevel==null) {
			Category topLevelCategory=new Category();
			topLevel.setName(req.getTopLevelCategory());
			topLevelCategory.setLevel(1);
			topLevel=categoryRepository.save(topLevelCategory);
		}
		
		Category secondLevel=categoryRepository.findByNameAndParent(req.getSecondLevelCategory(),topLevel.getName());
		if(secondLevel==null) {
			Category secondLevelCategory=new Category();
			secondLevelCategory.setName(req.getSecondLevelCategory());
			secondLevelCategory.setParentCategory(topLevel);
			secondLevelCategory.setLevel(2);
			topLevel=categoryRepository.save(secondLevelCategory);
		}
		
		Category thirdLevel=categoryRepository.findByNameAndParent(req.getThirdLevelCategory(),secondLevel.getName());
		if(thirdLevel==null) {
			Category thirdLevelCategory=new Category();
			thirdLevelCategory.setName(req.getThirdLevelCategory());
			thirdLevelCategory.setParentCategory(secondLevel);
			thirdLevelCategory.setLevel(3);
			topLevel=categoryRepository.save(thirdLevelCategory);
		}
		
		
		Product product=new Product();
		product.setTitle(req.getTitile());
		product.setColor(req.getColor());
		product.setDescription(req.getDescription());
		product.setBrand(req.getBrand());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountPresent(req.getDiscountPresent());
		product.setPrice(req.getPrice());
		product.setImageUrl(req.getImageUrl());
		product.setSizes(req.getSize());
		product.setCategory(thirdLevel);
		product.setQuantity(req.getQuantity());
		product.setCretedAt(LocalDateTime.now());
		
		Product savedProduct=productRepository.save(product);
		
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product =findById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product Deleted Successfully";
	}

	private Product findById(Long productId) {
		
		return null;
	}

	@Override
	public Product updateProduct(Long productId, Product req) throws ProductException {
		Product product=findById(productId);
		
		if(req.getQuantity()!=0) {
			product.setQuantity(req.getQuantity());
		}
		return productRepository.save(product);
	}

	@Override
	public Product findProductById(Long id) throws ProductException {
		Optional<Product> opt=productRepository.findById(id);
		if(opt.isPresent()) {
				return opt.get();
		}
		throw new ProductException("Product Not Found "+id);
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> color, List<String> sizes, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {
		
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		List<Product> products=productRepository.filteProduct(category, minPrice, maxPrice, minDiscount, sort);
		if(!color.isEmpty()) {
			products=products.stream().filter(p->color.stream().anyMatch(c->c.equalsIgnoreCase(p.getColor())))
					.collect(Collectors.toList());
		}
		
		if(stock!=null) {
			if(stock.equals("in_stock")){
				products=products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList());
			}
			else if(stock.equals("out_of_stock")) {
				products=products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList());
				
			}
		}
		
		int startIndex=(int)pageable.getOffset();
		int endIndex=Math.min(startIndex+pageable.getPageSize(),products.size());
		
		List<Product> pageCotent=products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts=new PageImpl<>(pageCotent,pageable,products.size());
		return filteredProducts;
	}

}
