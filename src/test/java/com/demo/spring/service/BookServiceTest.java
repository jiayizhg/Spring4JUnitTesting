package com.demo.spring.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mock;

import com.demo.spring.dao.BookDAO;
import com.demo.spring.model.Book;

@TestMethodOrder(OrderAnnotation.class)
public class BookServiceTest {
	
	private BookServiceImpl bookServiceImpl;
	
	@Mock
	BookDAO bookDAO;
	
	Book expectedBook = new Book();
	
	@BeforeEach
	public void setUp() {
		
		// Creating test data which’ll be returned as a response in the rest service.
		expectedBook.setId(50L);
		expectedBook.setAuthor("Christina Alfaro");
		expectedBook.setTitle("Word of Time");
				
		bookDAO = mock(BookDAO.class);
		bookServiceImpl = new BookServiceImpl();
		bookServiceImpl.setBookDAO(bookDAO);
	}
	
	/*---------- Testing POST Methods ---------- */
	@Test
	@Order(1)
	@RepeatedTest(3)
	@DisplayName("Testing POST method (1)")
	public void addNewBookService() {
		
		// Given the return value for method save()
		when(bookDAO.save(any(Book.class))).thenReturn(expectedBook.getId());
		
		// Get the actual value from calling save() method
		long actualValue = bookServiceImpl.save(expectedBook);
		
		// Then the expected value should equal to the actual value
		assertEquals(expectedBook.getId(), actualValue, "Add a book record that should return its id");
		
		// Verify that the save() method of the bookDAO interface is invoked exactly once.
		verify(bookDAO, times(1)).save(any(Book.class));
	}
	

	@Test
	@Order(2)
	@DisplayName("Testing POST method (2)")
	public void addAndReturnNewRecordTest() {
		
		// Given the return value for method addAndReturnNewRecord()
		when(bookDAO.addAndReturnNewRecord(any(Book.class))).thenReturn(expectedBook);
		
		// Get the actual value from calling addAndReturnNewRecord() method
		Book actualBook = bookServiceImpl.addAndReturnNewRecord(expectedBook);
		
		// Then the expected value should equal to the actual value
		assertEquals(expectedBook, actualBook, "Add a book record that should return the new record");
		
		// Verify that the addAndReturnNewRecord() method of the bookDAO interface is invoked exactly once.
		verify(bookDAO, times(1)).addAndReturnNewRecord(any(Book.class));
	}
	
	/*---------- Testing GET Methods ---------- */
	@Test
	@Order(3)
	@DisplayName("Testing GET method (1)")
	public void getBookByIdService() {

		// Given the return value for method get()
		when(bookDAO.get(anyLong())).thenReturn(expectedBook);
		
		// Get the actual value from calling get() method
		Book actualBook = bookServiceImpl.get(expectedBook.getId());
		
		// Then the expected value should equal to the actual value
		assertEquals(expectedBook, actualBook, "Get a book record that should return its id");
		
		verify(bookDAO, times(1)).get(anyLong());
	}
	
	@Test
	@DisplayName("Testing GET method (2)")
	@Order(4)
	public void getAllBooksService() {
		
		// Given the return value for method list()
		when(bookDAO.list()).thenReturn(Arrays.asList(expectedBook));
		
		// Get the actual value from calling list() method
		List<Book> actualBook = bookServiceImpl.list();
		
		// Then the expected book should equal to the actual value
		assertEquals(expectedBook, actualBook.get(0));
		
		verify(bookDAO, times(1)).list();
	}
	
	/*---------- Testing PUT Methods ---------- */
	@Test
	@DisplayName("Testing PUT method")
	@Order(5)
	public void updateBookServiceTest() {

		Book updateBook = new Book();
		updateBook.setId(55L);
		updateBook.setAuthor("Jerry Curtis");
		updateBook.setTitle("Diary of a Cat");
		
		long expectedValue = 0;
		
		// Given the return value for method update()
		when(bookDAO.update(anyLong(), any(Book.class))).thenReturn(expectedValue);
		
		// Get the actual value from calling update() method
		long actualValue = bookServiceImpl.update(expectedBook.getId(), updateBook);
		
		// Then the expected book should equal to the actual value
		assertEquals(expectedValue, actualValue, "Update book record");
		
		verify(bookDAO, times(1)).update(anyLong(), any(Book.class));
	}
}
