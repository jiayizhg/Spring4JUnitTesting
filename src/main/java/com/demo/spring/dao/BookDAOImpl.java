package com.demo.spring.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.spring.model.Book;

@Repository
public class BookDAOImpl implements BookDAO{
	
   @PersistenceContext
    private EntityManager entityManager;
	 

	public BookDAOImpl(SessionFactory sessionFactory) {
	
		this.sessionFactory = sessionFactory;
	}

	private SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public long save(Book book) {
		
		sessionFactory.getCurrentSession().save(book);
		
		return book.getId();
	}

	@Override
	public Book get(long id) {
		
		Book book = sessionFactory.getCurrentSession().get(Book.class, id);
		
		return book;
	}

	@Override
	public List<Book> list() {
		
		List <Book> list = sessionFactory.getCurrentSession().createQuery("from Book").list();
		
		return list;
	}

	@Override
	public long update(long id, Book book) {

		Session session = sessionFactory.getCurrentSession();
		
		Book oldbook = session.byId(Book.class).load(id);
		
		if (oldbook == null)
			return -1;
		
		oldbook.setTitle(book.getTitle());
		oldbook.setAuthor(book.getAuthor());
		
	    session.flush();
		
		return 0;
	}

	@Override
	public void delete(long id) {
		Session session = sessionFactory.getCurrentSession();
		Book book = session.byId(Book.class).load(id);
		session.delete(book);
	}

	@Override
	public Book addAndReturnNewRecord(Book book) {
		
		sessionFactory.getCurrentSession().save(book);
		
		return book;
	}

	@Override
	public Book getByIDAndAuthor(long id, String author) {
		Session session = sessionFactory.getCurrentSession();
		String hql = "from book where id = 50 and author = 'author'";
		
		StringBuffer sql = new StringBuffer();
        sql.append(" from Book" + " where id =: id");
        sql.append( " and author =:author " );
            
		Query query = session.createQuery(sql.toString());
		
		List<Book> book = query.list();
		
		return book.get(0);
	}

}
