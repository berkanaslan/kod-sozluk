package com.berkanaslan.eksisozlukclone.controller;

import com.berkanaslan.eksisozlukclone.model.Book;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("book")
public class BookController extends BaseEntityController<Book> {
    @Override
    public Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    public String getRequestPath() {
        return "book";
    }

    ArrayList<Book> books = new ArrayList<>();

    @Override
    public List<Book> findAll() {
        books.add(new Book("Book 1", "Author 1"));
        books.add(new Book("Book 2", "Author 2"));
        books.add(new Book("Book 3", "Author 3"));
        return books;
    }
}
