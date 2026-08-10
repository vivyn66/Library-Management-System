package org.jsp.controller;



import java.util.List;
import java.util.Map;

import org.jsp.entity.Book;
import org.jsp.response.ResponseStructure;
import org.jsp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
public class BookController {
	
	
	@Autowired
	private BookService bookService;	
	
	@Operation(
		    summary = "Check API status",
		    description = "Returns a message confirming that the Library Management System API is running")
	@GetMapping("/")
	public String home() {
		return "Library management Project API running";
	}
	
	@Operation(
			summary = "Save a new book",
			description = "Creates and saves a new book in the database")
	@PostMapping("/books")
	public ResponseEntity<ResponseStructure<Book>> saveBook(@Valid @RequestBody Book book){
		return new ResponseEntity<>(bookService.saveBook(book),HttpStatus.CREATED);
	}
	
	@Operation(
		    summary = "Save multiple books",
		    description = "Creates and saves multiple books in the database")
	@PostMapping("/books/all")
	public ResponseEntity<ResponseStructure<List<Book>>> saveAllBook(@Valid @RequestBody List<Book> book){
		
		return new ResponseEntity<>(bookService.savesAllBook(book),HttpStatus.CREATED);
	}
	
	
	@Operation(
		    summary = "Get book by ID",
		    description = "Fetches a book from the database using its book ID")
	@GetMapping("/book/{id}")
	public ResponseEntity<ResponseStructure<Book>> findById(@PathVariable int id){
		return new ResponseEntity<>(bookService.findById(id),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Get all books",
		    description = "Fetches all books available in the database")
	@GetMapping("/book/all")
	public ResponseEntity<ResponseStructure<List<Book>>> getAllBooks(){
		
		return new ResponseEntity<>(bookService.getAllBooks(),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Update a book",
		    description = "Updates an existing book using the book ID")
	@PutMapping("/book")
	public ResponseEntity<ResponseStructure<Book>> updateBookPut(@Valid @RequestBody Book book){
		
		return new ResponseEntity<>(bookService.updateBookPut(book),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Partially update a book",
		    description = "Updates only the provided fields of an existing book")
	@PatchMapping("/book/{id}")
	public ResponseEntity<ResponseStructure<Book>> updateBookPatch(@RequestBody Map<String, Object> data, @PathVariable int id){
		
		return new ResponseEntity<>(bookService.updateBookPatch(data,id),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Delete a book",
		    description = "Deletes an existing book from the database using its book ID")
	@DeleteMapping("/book/{id}")
	public ResponseEntity<ResponseStructure<String>> deleteBook(@PathVariable int id){
		
		return new ResponseEntity<>(bookService.deleteBook(id),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Find books by author",
		    description = "Fetches all books written by the specified author")
	@GetMapping("/book/author/{author}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByAuthor(@PathVariable String author){
		
		return new ResponseEntity<>(bookService.findByAuthor(author),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Find books by category",
		    description = "Fetches all books belonging to the specified category")
	@GetMapping("/book/category/{category}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByCategory(@PathVariable String category){
		return new ResponseEntity<>(bookService.findByCategory(category),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Find books above a price",
		    description = "Fetches all books whose price is greater than the specified value")
	@GetMapping("/book/price/{price}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByPriceGreaterThan(
	        																@PathVariable double price) {
	    return new ResponseEntity<>(bookService.findByPriceGreaterThan(price),HttpStatus.OK);
	}
	
	
	@Operation(
		    summary = "Find books within a price range",
		    description = "Fetches all books whose price falls between the specified minimum and maximum values")
	@GetMapping("/book/price/btw/{min}/{max}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByPriceBetween(@PathVariable double min,
	        @PathVariable double max){
		 return new ResponseEntity<>(bookService.findByPriceBetween(min, max),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Find books by category and author",
		    description = "Fetches books matching both the specified category and author")
	@GetMapping("/book/category/{category}/author/{author}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByCategoryAndAuthor(@PathVariable String category,@PathVariable String author){
		return new ResponseEntity<>(bookService.findByCategoryAndAuthor(category, author),HttpStatus.OK);
	}
	
	@Operation(
		    summary = "Find books by category or author",
		    description = "Fetches books matching either the specified category or the specified author")
	@GetMapping("/book/categoryOrAuthor/{category}/{author}")
	public ResponseEntity<ResponseStructure<List<Book>>> findByCategoryOrAuthor(
										@PathVariable String category,@PathVariable String author) {

	    return new ResponseEntity<>(bookService.findByCategoryOrAuthor(category, author),HttpStatus.OK);
	}
	
	
	@Operation(
		    summary = "Get books with pagination",
		    description = "Fetches books page by page using the specified page number and page size")
	@GetMapping("/book/page")
	public ResponseEntity<ResponseStructure<Page<Book>>> getByPagination(@RequestParam int pageNumber, 
			@RequestParam int pageSize){
		
		return new ResponseEntity<>(bookService.getByPagination(pageNumber, pageSize),HttpStatus.OK);
	}
	
	
	@Operation(
		    summary = "Get books sorted",
		    description = "Fetches all books sorted in ascending order based on the specified field")
	@GetMapping("/book/sort/asc/{fieldName}")
	public ResponseEntity<ResponseStructure<List<Book>>> getBySortingAsc(@PathVariable String fieldName){
		
		return new ResponseEntity<>(bookService.getBySorting(fieldName),HttpStatus.OK);
	}
	
	
	@Operation(
		    summary = "Get books with pagination and sorting",
		    description = "Fetches a specific page of books sorted in descending order by the specified field")	
	@GetMapping("/book/pagesort")
	public ResponseEntity<ResponseStructure<Page<Book>>> getByPaginationAndSortingDesc(@RequestParam int pageNumber,
											@RequestParam int pageSize,@RequestParam String fieldName){
		return new ResponseEntity<>(bookService.getByPaginationAndSortDesc(pageNumber, pageSize, fieldName),HttpStatus.OK);
	}
 	
	
}
