package com.librarymng.library.entity;

import java.util.ArrayList;
import java.util.List;

public class Patron
{
    private final long id;
    private String name;
    private String contact;
    private final List<String> borrowingHistory;

    private Patron(PatronBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.contact = builder.contact;
        this.borrowingHistory = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact() {
        return contact;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public List<String> getBorrowingHistory() {
        return new ArrayList<>(borrowingHistory);
    }

    public void addToBorrowingHistory(String isbn) {
        borrowingHistory.add(isbn);
    }

    public static class PatronBuilder {
        private long id;
        private String name;
        private String contact;

        public PatronBuilder id(long id) {
            this.id = id;
            return this;
        }

        public PatronBuilder name(String name) {
            this.name = name;
            return this;
        }

        public PatronBuilder contact(String contact) {
            this.contact = contact;
            return this;
        }

        public Patron build() {
            return new Patron(this);
        }
    }
}
