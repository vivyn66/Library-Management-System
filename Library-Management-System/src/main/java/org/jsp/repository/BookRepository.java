package org.jsp.repository;

import java.util.List;

import org.jsp.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer>{
	
	//Custom methods
	List<Book> findByAuthor(String author);
	
	List<Book> findByCategory(String category);
	
	List<Book> findByPriceGreaterThan(double price);
	
	List<Book> findByPriceBetween(double min,double max);
	
	List<Book> findByCategoryAndAuthor(String category, String author);
	
	List<Book> findByCategoryOrAuthor(String category, String author);
}
