package com.librarymng.library.entity;

import com.librarymng.library.contract.Reservable;
import com.librarymng.library.contract.Searchable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Branch implements Reservable, Searchable {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final long branchId;
    private final Map<String, Book> books;
    private final Map<Long, Patron> patrons;
    private final Map<String, Reservation> reservations;

    public Branch(long branchId) {
        this.branchId = branchId;
        this.books = new HashMap<>();
        this.patrons = new HashMap<>();
        this.reservations = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
        logger.info("Added book {} to branch {}", book.getIsbn(), branchId);
    }

    public void removeBook(String isbn) {
        books.remove(isbn);
        reservations.remove(isbn);
        logger.info("Removed book {} from branch {}", isbn, branchId);
    }

    public void addPatron(Patron patron) {
        patrons.put(patron.getId(), patron);
        logger.info("Added patron {} to branch {}", patron.getId(), branchId);
    }

    public void updatePatron(long id, String name, String contact) {
        Patron patron = patrons.get(id);
        if (patron != null) {
            patron.setName(name);
            patron.setContact(contact);
            logger.info("Updated patron {} in branch {}", id, branchId);
        } else {
            logger.error("Patron {} not found in branch {}", id, branchId);
            throw new IllegalArgumentException("Patron not found");
        }
    }

    @Override
    public List<Book> searchByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        logger.info("Found {} books with title containing '{}'", result.size(), title);
        return result;
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        logger.info("Found {} books by author containing '{}'", result.size(), author);
        return result;
    }

    @Override
    public Book searchByIsbn(String isbn) {
        Book book = books.get(isbn);
        logger.info(book != null ? "Found book {}" : "Book {} not found", isbn);
        return book;
    }

    public void checkoutBook(String isbn, long patronId) {
        Book book = books.get(isbn);
        Patron patron = patrons.get(patronId);
        if (book != null && patron != null && book.isAvailable()) {
            book.setAvailable(false);
            patron.addToBorrowingHistory(isbn);
            logger.info("Book {} checked out to patron {} in branch {}", isbn, patronId, branchId);
            return;
        }
        logger.error("Checkout failed: Book {} or patron {} invalid or book unavailable", isbn, patronId);
    }

    public void returnBook(String isbn) {
        Book book = books.get(isbn);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            logger.info("Book {} returned in branch {}", isbn, branchId);
            Reservation reservation = reservations.get(isbn);
            if (reservation != null && reservation.hasReservations()) {
                Long patronId = reservation.getNextPatron();
                logger.info("Notified patron {}: Book {} is available", patronId, isbn);
            }
            return;
        }
        logger.error("Return failed: Book {} not found or not checked out", isbn);
    }

    @Override
    public void reserveBook(String isbn, long patronId) {
        Book book = books.get(isbn);
        Patron patron = patrons.get(patronId);
        if (book != null && patron != null && !book.isAvailable()) {
            reservations.computeIfAbsent(isbn, k -> new Reservation(isbn)).addPatron(patronId);
            logger.info("Patron {} reserved book {} in branch {}", patronId, isbn, branchId);
            return;
        }
        logger.error("Reservation failed: Book {} or patron {} invalid or book available", isbn, patronId);
    }

    public List<Book> getAvailableBooks() {
        List<Book> available = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.isAvailable()) available.add(book);
        }
        return available;
    }

    public long getBranchId() {
        return branchId;
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public Map<Long, Patron> getPatrons() {
        return patrons;
    }

    public Map<String, Reservation> getReservations() {
        return reservations;
    }
}
