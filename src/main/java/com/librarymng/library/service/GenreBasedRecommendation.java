package com.librarymng.library.service;

import com.librarymng.library.contract.RecommendationStrategy;
import com.librarymng.library.entity.Book;
import com.librarymng.library.entity.Patron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class GenreBasedRecommendation implements RecommendationStrategy {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<Book> recommendBooks(Patron patron, Map<String, Book> books) {
        List<Book> recommendations = new ArrayList<>();
        Set<String> genres = new HashSet<>();
        for (String isbn : patron.getBorrowingHistory()) {
            Book book = books.get(isbn);
            if (book != null) genres.add(book.getGenre());
        }
        for (Book book : books.values()) {
            if (book.isAvailable() && genres.contains(book.getGenre()) &&
                    !patron.getBorrowingHistory().contains(book.getIsbn())) {
                recommendations.add(book);
            }
        }
        log.info("Generated {} genre-based recommendations for patron {}", recommendations.size(), patron.getId());
        return recommendations;

    }
}
