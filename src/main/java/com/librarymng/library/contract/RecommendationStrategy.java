package com.librarymng.library.contract;

import com.librarymng.library.entity.Book;
import com.librarymng.library.entity.Patron;

import java.util.List;
import java.util.Map;

public interface RecommendationStrategy
{
    List<Book> recommendBooks(Patron patron, Map<String, Book> books);
}
