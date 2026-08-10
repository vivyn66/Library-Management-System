package org.jsp.service;

import java.util.List;

import java.util.Map;
import java.util.Optional;

import org.jsp.entity.Book;
import org.jsp.exception.IdNotFoundException;
import org.jsp.exception.NoRecordsAvailableException;
import org.jsp.repository.BookRepository;
import org.jsp.response.ResponseStructure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class BookService{
	
	
	private final BookRepository bookRepository;
	
	public BookService(BookRepository repository) {
        this.bookRepository = repository;
    }

	public ResponseStructure<Book> saveBook(Book book){
		Book books = bookRepository.save(book);
		ResponseStructure<Book> res = new ResponseStructure<Book>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Record saved Successfully");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> savesAllBook( List<Book> book){
		
		List<Book> books = bookRepository.saveAll(book);
		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.CREATED.value());
		res.setMessage("Records inserted successfully");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<Book> findById(int id){
		Optional<Book> opt =  bookRepository.findById(id);
		
		if(opt.isPresent()) {
			ResponseStructure<Book> res= new ResponseStructure<Book>();
			Book book = opt.get();
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Record with ID: " + id + " fetched successfully");
			res.setData(book);
			
			return res;	
		}else 
			throw new IdNotFoundException("Id doesn't exists");
	}
	
	
	public ResponseStructure<List<Book>> getAllBooks(){
		List<Book> books = bookRepository.findAll();
		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		if(books.isEmpty()) {
			throw new NoRecordsAvailableException("No records available in the Database");
		}
		
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("All records fetched");
			res.setData(books);
			
			return res;
		
	}
	
	public ResponseStructure<Book> updateBookPut( Book book){
		ResponseStructure<Book> res = new ResponseStructure<>();
		if(book.getBookId()==0) {
			throw new IdNotFoundException("ID must be passed to update a record");
		}
		Optional<Book> opt = bookRepository.findById(book.getBookId());
		if(opt.isPresent()) {
			Book b = bookRepository.save(book);
			
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Record updated successfully");
			res.setData(b);
			
			return res;
		}
		throw new IdNotFoundException("ID doesn't exist in the database");
	}
	
	public ResponseStructure<Book> updateBookPatch(Map<String, Object> data,int id){
		ResponseStructure<Book> res = new ResponseStructure<Book>();
		
		Optional<Book> opt = bookRepository.findById(id);
		if(opt.isPresent()) {
			Book book = opt.get();
			
			for(Map.Entry<String, Object> entry : data.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				
				switch(key) {
				case "title":
					book.setTitle((String) value);
					break;
				
				case "author":
					book.setAuthor((String)value);
					break;
					
				case "category":
					book.setCategory((String)value);
					break;
					
				case "price":
	                book.setPrice((Double) value);
	                break;

	            case "quantity":
	                book.setQuantity((Integer) value);
	                break;
	                
	            default:
	            		throw new IllegalArgumentException("Invalid field: " + key);				
	            		}
			}
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Record updated succesfull");
			res.setData(bookRepository.save(book));
			
			return res;
		}
		throw new IdNotFoundException("ID doesn't exist in the database");
	}
	
	
	public ResponseStructure<String> deleteBook(int id){
		Optional<Book> opt = bookRepository.findById(id);
		if(opt.isPresent()) {
			ResponseStructure<String> res = new ResponseStructure<String>();
			bookRepository.deleteById(id);
			res.setStatusCode(HttpStatus.OK.value());
			res.setMessage("Book deleted successfully");
			res.setData("Book deleted successfully");
			
			return res;
		}
		throw new IdNotFoundException("ID doesn't exist in the database");
	}
	
	
	public ResponseStructure<List<Book>> findByAuthor(String author){
		
		List<Book> books = bookRepository.findByAuthor(author);
		if(books.isEmpty()) {
			throw new NoRecordsAvailableException("No records available");
		}
		ResponseStructure<List<Book>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Records fetched by author");
		res.setData(books);
		
		return res;
	}

	
	public ResponseStructure<List<Book>> findByCategory(String category){
		List<Book> books = bookRepository.findByCategory(category);
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No records available for this category");
		
		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Records fetched successfull");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> findByPriceGreaterThan(double price){
		List<Book> books = bookRepository.findByPriceGreaterThan(price);
		
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No records available for the price greater than price: "+price);
		
		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Books fetched with price greater than: "+price);
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> findByPriceBetween(double min,double max){
		List<Book>  books = bookRepository.findByPriceBetween(min,max);
		
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No books available between the price range you've provided");
		
		ResponseStructure<List<Book>> res= new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Books fetched successfully");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> findByCategoryAndAuthor(
	        String category, String author) {
		List<Book> books = bookRepository.findByCategoryAndAuthor(category, author);
		
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No books available for the category and author you've provided");

		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Books with author: " + author + " and category: " + category + " fetched successfully");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> findByCategoryOrAuthor(String category, String author) {

	    List<Book> books =bookRepository.findByCategoryOrAuthor(category, author);

	    if (books.isEmpty()) 
	        throw new NoRecordsAvailableException(
	                "No books available for the given category or author");

	    ResponseStructure<List<Book>> res = new ResponseStructure<>();
	    res.setStatusCode(HttpStatus.OK.value());
	    res.setMessage("Books fetched successfully");
	    res.setData(books);

	    return res;
	}
	
	public ResponseStructure<Page<Book>> getByPagination(int pageNumber, int pageSize){
		Page<Book> page = bookRepository.findAll(PageRequest.of(pageNumber, pageSize));
		
		if(page.isEmpty())
			throw new  NoRecordsAvailableException("No records available for the requested page");
		
		ResponseStructure<Page<Book>> res = new ResponseStructure<Page<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Records fetched with respect to pagination");
		res.setData(page);
		
		return res;
	}
	
	public ResponseStructure<List<Book>> getBySorting(String fieldName){
		Sort sort = Sort.by(fieldName).ascending();
		List<Book> books = bookRepository.findAll(sort);
		
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No records available");
		
		ResponseStructure<List<Book>> res = new ResponseStructure<List<Book>>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Books sorted successfully");
		res.setData(books);
		
		return res;
	}
	
	public ResponseStructure<Page<Book>> getByPaginationAndSortDesc(
	        int pageNumber, int pageSize, String fieldName) {
		
		Page<Book> books = bookRepository.findAll(PageRequest.of(pageNumber, 
				pageSize,Sort.by(fieldName).descending()));
		if(books.isEmpty())
			throw new NoRecordsAvailableException("No records available for this sorting and pagination");
		
		ResponseStructure<Page<Book>> res = new ResponseStructure<>();
		res.setStatusCode(HttpStatus.OK.value());
		res.setMessage("Records fetched successfully");
		res.setData(books);
		
		return res;
	}
	
}
