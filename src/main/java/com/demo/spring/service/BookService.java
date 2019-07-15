package com.demo.spring.service;

import java.util.List;

import com.demo.spring.model.Book;

public interface BookService {
	// Save the record
	long save(Book book);
	
	Book addAndReturnNewRecord(Book book);
	
	// Get a single record by ID
	Book get(long id);
	
	// Get a single record by ID and author
	Book getByIDAndAuthor(long id, String author);
	
	// Get all the records
	List<Book> list();
	
	// Update the record
	long update(long id, Book book);
	
	// Delete a record
	void delete(long id);
}
