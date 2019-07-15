package com.demo.spring.dao;

import java.util.List;

import com.demo.spring.model.Book;

public interface BookDAO {
	
	// Save the record return book ID 
	long save(Book book);
	
	Book addAndReturnNewRecord(Book book);
	
	// Get a single record
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
