package com.librarymng.library.service;

import com.librarymng.library.contract.RecommendationStrategy;
import com.librarymng.library.entity.Book;
import com.librarymng.library.entity.Branch;
import com.librarymng.library.entity.Patron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryManagementSystem
{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Map<Long, Branch> branches;
    private RecommendationStrategy recommendationStrategy;

    public LibraryManagementSystem() {
        this.branches = new HashMap<>();
        this.recommendationStrategy = new HistoryBasedRecommendation();
    }

    public void addBranch(long branchId) {
        branches.put(branchId, new Branch(branchId));
        logger.info("Added branch {}", branchId);
    }

    public boolean transferBook(String isbn, long fromBranchId, long toBranchId) {
        Branch fromBranch = branches.get(fromBranchId);
        Branch toBranch = branches.get(toBranchId);
        if (fromBranch != null && toBranch != null) {
            Book book = fromBranch.searchByIsbn(isbn);
            if (book != null && book.isAvailable()) {
                fromBranch.removeBook(isbn);
                Book newBook =  new Book.BookBuilder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .author(book.getAuthor())
                        .publicationYear(book.getPublicationYear())
                        .branchId(toBranchId)
                        .build();
                toBranch.addBook(newBook);
                logger.info("Transferred book {} from branch {} to {}", isbn, fromBranchId, toBranchId);
                return true;
            }
        }
        logger.error("Transfer failed: Book {}, fromBranch {}, or toBranch {} invalid", isbn, fromBranchId, toBranchId);
        return false;
    }

    public List<Book> getRecommendations(long patronId, long branchId) {
        Branch branch = branches.get(branchId);
        if (branch != null) {
            Patron patron = branch.getPatrons().get(patronId);
            if (patron != null) {
                return recommendationStrategy.recommendBooks(patron, branch.getBooks());
            }
        }
        logger.error("Recommendation failed: Patron {} or branch {} invalid", patronId, branchId);
        return new ArrayList<>();
    }

    public void setRecommendationStrategy(RecommendationStrategy strategy) {
        this.recommendationStrategy = strategy;
        logger.info("Recommendation strategy updated");
    }

    public Map<Long, Branch> getBranches() {
        return branches;
    }

    public RecommendationStrategy getRecommendationStrategy() {
        return recommendationStrategy;
    }
}
