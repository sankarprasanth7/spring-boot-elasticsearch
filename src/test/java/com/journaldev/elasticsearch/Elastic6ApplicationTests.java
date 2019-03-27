package com.journaldev.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.journaldev.elasticsearch.bean.Book;
import com.journaldev.elasticsearch.dao.BookDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Elastic6ApplicationTests {
	
	@Autowired
	BookDao bookDao;

	@Test
	public void contextLoads() {
		Book book = new Book();
		book.setAuthor("Qwerty");
		book.setTitle("helloworld");
		book.setPrice(20);
		bookDao.insertBook(book);
	}

}
