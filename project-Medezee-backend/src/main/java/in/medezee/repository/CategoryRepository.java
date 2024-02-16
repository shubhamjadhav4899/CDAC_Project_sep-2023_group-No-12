package in.medezee.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.medezee.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	public Category findByName(String name);
	
	@Query("SELECT c FROM Category c where c.name=:name and c.parentCategory.name=:parentCategoryName")
	public Category findByNameAndParent(@Param("name") String name,@Param("parentCategoryName")String parentCategoryName);
}
