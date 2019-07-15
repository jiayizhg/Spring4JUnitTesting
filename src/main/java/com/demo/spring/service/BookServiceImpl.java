package com.demo.spring.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dao.BookDAO;
import com.demo.spring.model.Book;

@Service
public class BookServiceImpl implements BookService {
	
	private BookDAO bookDAO;
	
	@Autowired
	public void setBookDAO(BookDAO bookDAO) {
		this.bookDAO = bookDAO;
	}
	
	@Override
	@Transactional
	public long save(Book book) {	
		return bookDAO.save(book);		
	}

	@Override
	@Transactional
	public Book get(long id) {
		return bookDAO.get(id);
	}

	@Override
	@Transactional
	public List<Book> list() {
		return bookDAO.list();
	}

	@Override
	@Transactional
	public long update(long id, Book book) {
		if (bookDAO.update(id, book) == 0)
			return 0;
		
		return -1;
	}

	@Override
	@Transactional
	public void delete(long id) {
		bookDAO.delete(id);
	}

	@Override
	@Transactional
	public Book addAndReturnNewRecord(Book book) {
		return bookDAO.addAndReturnNewRecord(book);		
	}

	@Override
	public Book getByIDAndAuthor(long id, String author) {
		return bookDAO.getByIDAndAuthor(id, author);
	}

}
