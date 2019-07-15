package com.demo.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.model.Book;
import com.demo.spring.service.BookService;

@CrossOrigin("*")
@RestController
public class BookController {
	
	private BookService bookService;
	
	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}
	
	// Get all books
	@RequestMapping(value = "/api/book", method = RequestMethod.GET)
	public List<Book> list() {
		List<Book> books = new ArrayList<>();
		books = bookService.list();
		
		return books;
	}

	// Add a new book
	// Returns the id of the book if added successfully
	// Return -1 if failed adding a new book
	@RequestMapping(value = "/api/book", method = RequestMethod.POST)
	public long save(@RequestBody Book book) {
		
		long id = -1;
		id = bookService.save(book);
		
		if (id < 0)
			return -1;
		
		return id;
	}
	
	// Add a new book record and return the object
	@RequestMapping(value = "/api/book/newBook", method = RequestMethod.POST)
	public Book addAndReturnNewRecord(@RequestBody Book book) {
					
		Book newBook = new Book();
		
		newBook = bookService.addAndReturnNewRecord(book);
		
		return newBook;
	}
	
	// Get a book by id
	@RequestMapping(value = "/api/book/{id}", method = RequestMethod.GET)
	public Book get(@PathVariable("id") long id) {
		Book book = bookService.get(id);
		return book;
	}
	
	// Get a book by id and author
	@RequestMapping(value = "/api/book/{id}/{author}", method = RequestMethod.GET)
	public Book getByIDAndAuthor(@PathVariable("id") long id, @PathVariable("author") String author) {
		Book book = bookService.getByIDAndAuthor(id, author);
		return book;
	}
	
	// Update a book record by id
	@RequestMapping(value = "/api/book/{id}", method = RequestMethod.PUT)
	public long update(@PathVariable("id") long id, @RequestBody Book book) {
		bookService.update(id, book);
		return 0;
	}
	
	// Delete a book record by id
	@RequestMapping(value = "/api/book/{id}", method = RequestMethod.DELETE)
	public long delete(@PathVariable("id") long id) {
		bookService.delete(id);
		return 0;
	}
	
}
