package com.librarymng.library.contract;

import com.librarymng.library.entity.Book;

import java.util.List;

public interface Searchable
{
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    Book searchByIsbn(String isbn);
}
