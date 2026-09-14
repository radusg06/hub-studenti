package ro.hubstudentesc.persistence.repository.marketplace;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.hubstudentesc.persistence.entity.marketplace.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
