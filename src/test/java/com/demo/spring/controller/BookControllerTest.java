package com.demo.spring.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.demo.spring.model.Book;
import com.demo.spring.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BookControllerTest {

	// Mocking BookService to return mocked data when calling
	// a method from this service
	@Mock
	private BookService bookService;
	
	private BookController bookController;
	
	Book book = new Book();
	
	String jsonStr = "";
	
	@BeforeEach
	public void setUp() throws JsonProcessingException {
		
		// Creating test data which’ll be returned as a response in the rest service.
		book.setId(50L);
		book.setAuthor("Christina Alfaro");
		book.setTitle("Word of Time");
		
		ObjectMapper objectMapper = new ObjectMapper();
		jsonStr = objectMapper.writeValueAsString(book); 
		
		// Creating a mock object of bookService interface.
		bookService = mock(BookService.class);
		// Inject mocked object into bookController
		bookController = new BookController(bookService);
	}
	
	@AfterEach
	public void cleanUp() {
		
		// Verify that after each response, no more interactions are made to the bookService
		verifyNoMoreInteractions(bookService);
	}
	
	/*---------- Testing GET Methods ---------- */
	@Test
	@DisplayName("Testing GET method (1)")
	public void getBookByIdTest() throws Exception {
		
		// Given the return value for method get()
		when(bookService.get(anyLong())).thenReturn(book);
		
		// Build a MockMvc instance by registering bookController instance
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		
		// When executing a GET request to /api/book/{id}
		mockMvc.perform(get("/api/book/{id}", 50).contentType(MediaType.APPLICATION_JSON_UTF8)) 
			.andExpect(status().isOk()) // Then the HTTP status code should be 200 (OK)
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) // Checking content-type and character set of the response
			.andExpect(jsonPath("$.id").value(50)) // id should equal to 50
			.andExpect(jsonPath("$.title").value("Word of Time")) // title should equal to "Word of Time"
			.andExpect(jsonPath("$.author").value("Christina Alfaro")) // author should equal to "Christina Alfaro"
			.andDo(print());
		
		// Verify that the get() method of the bookService interface is invoked exactly once.
		verify(bookService, times(1)).get(anyLong());
	}
	
	@Test
	@DisplayName("Testing GET method (2)")
	public void getAllBooksTest() throws Exception {
		
		// Creating a book object to pass into bookService.list()
		Book book1 = new Book();
		book1.setId(55L);
		book1.setAuthor("Jerry Curtis");
		book1.setTitle("Diary of a Cat");
		
		when(bookService.list()).thenReturn(Arrays.asList(book, book1));
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		
		mockMvc.perform(get("/api/book"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)))
			.andExpect(jsonPath("$[0].id").value(50))
			.andExpect(jsonPath("$[0].title").value("Word of Time"))
			.andExpect(jsonPath("$[0].author").value("Christina Alfaro"))
			.andExpect(jsonPath("$[1].id").value(55))
			.andExpect(jsonPath("$[1].title").value("Diary of a Cat"))
			.andExpect(jsonPath("$[1].author").value("Jerry Curtis"));
		
		verify(bookService, times(1)).list();
	}
	
	/*---------- Testing POST Methods ---------- */
	@Test
	@DisplayName("Testing POST method (1)")
	public void createNewBookTest() throws Exception {
		
		when(bookService.save(any(Book.class))).thenReturn(book.getId());
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		
		mockMvc.perform(post("/api/book").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("50"));
			
		verify(bookService, times(1)).save(any(Book.class));
	}
	
	@Test
	@DisplayName("Testing POST method (2)")
	public void addAndReturnNewRecordTest() throws Exception {
		
		when(bookService.addAndReturnNewRecord(any(Book.class))).thenReturn(book);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		
		mockMvc.perform(post("/api/book/newBook").content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.id").value(50))
				.andExpect(jsonPath("$.title").value("Word of Time"))
				.andExpect(jsonPath("$.author").value("Christina Alfaro"));
		
		verify(bookService, times(1)).addAndReturnNewRecord(any(Book.class));
	}
	
	/*---------- Testing PUT Methods ---------- */
	@Test
	@DisplayName("Testing PUT method")
	public void updateBookTest() throws Exception {
		
		long returnValue = 0;
		
		when(bookService.update(anyLong(), any(Book.class))).thenReturn(returnValue);
		
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
		
		mockMvc.perform(put("/api/book/{id}", 50).content(jsonStr)
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk())
				.andExpect(content().string("0"));
				
			
		verify(bookService, times(1)).update(anyLong(), any(Book.class));
	}

}
