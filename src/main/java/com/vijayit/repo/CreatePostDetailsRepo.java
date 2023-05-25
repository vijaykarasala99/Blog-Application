package com.vijayit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vijayit.entity.CreatePostDetails;

public interface CreatePostDetailsRepo extends JpaRepository<CreatePostDetails, Integer> {

    List<CreatePostDetails> findByDeletedFalse();
    
    List<CreatePostDetails> findByTitleContaining(String title);

    
/*
 *  @Modifying
    @Query("update Product p set deleted = true where p = :p")
    void delete(Product p);

    @Query("select p FROM Product p WHERE p.deleted = false")
    Page<Product> findAll(Pageable pageable);
}    
 */

	
}
