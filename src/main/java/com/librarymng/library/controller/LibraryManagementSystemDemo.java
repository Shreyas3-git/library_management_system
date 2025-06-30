package com.librarymng.library.controller;

import com.librarymng.library.entity.Book;
import com.librarymng.library.entity.Patron;
import com.librarymng.library.service.HistoryBasedRecommendation;
import com.librarymng.library.service.LibraryManagementSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LibraryManagementSystemDemo
{
    private static final Logger log = LoggerFactory.getLogger(LibraryManagementSystemDemo.class);
    
    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();

        system.addBranch(1L);
        system.addBranch(2L);

        if (!system.getBranches().containsKey(1L) || !system.getBranches().containsKey(2L)) {
            log.error("Failed to initialize branches");
            return;
        }

        Book book1 = new Book.BookBuilder()
                .bookId(1L)
                .isbn("ISBN001")
                .title("The Great Gatsby")
                .author("F. Scott Fitzgerald")
                .publicationYear(1925)
                .branchId(1L)
                .build();
        Book book2 = new Book.BookBuilder()
                .bookId(2L)
                .isbn("ISBN002")
                .title("1984")
                .author("George Orwell")
                .publicationYear(1949)
                .branchId(1L) // Corrected to branch 1
                .build();

        try {
            system.getBranches().get(1L).addBook(book1);
            system.getBranches().get(1L).addBook(book2);
            log.info("Added books to branch 1");
        } catch (NullPointerException e) {
            log.error("Failed to add books to branch 1: {}", e.getMessage());
            return;
        }

        Patron patron = new Patron.PatronBuilder()
                .id(101L)
                .name("John Doe")
                .contact("john@example.com")
                .build();
        try {
            system.getBranches().get(1L).addPatron(patron);
            log.info("Added patron to branch 1");
        } catch (NullPointerException e) {
            log.error("Failed to add patron to branch 1: {}", e.getMessage());
            return;
        }

        system.getBranches().get(1L).checkoutBook("ISBN001", 101L);
        log.info("Attempted checkout of ISBN001 by patron 101");

        system.getBranches().get(1L).reserveBook("ISBN001", 101L);
        log.info("Attempted reservation of ISBN001 by patron 101");

        system.getBranches().get(1L).returnBook("ISBN001");
        log.info("Attempted return of ISBN001");

        boolean transferSuccess = system.transferBook("ISBN001", 1L, 2L);
        if (transferSuccess) {
            log.info("Successfully transferred ISBN001 from branch 1 to branch 2");
        } else {
            log.error("Failed to transfer ISBN001");
        }

        system.setRecommendationStrategy(new HistoryBasedRecommendation());
        List<Book> recommendations = system.getRecommendations(101L, 1L);
        if (recommendations.isEmpty()) {
            log.info("No recommendations available for patron 101 in branch 1");
        } else {
            for (Book book : recommendations) {
                System.out.println("Recommended: " + book.getTitle());
            }
        }
    }

}
