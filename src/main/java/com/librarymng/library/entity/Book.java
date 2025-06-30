package com.librarymng.library.entity;


public class Book
{
    private final long bookId;
    private final String isbn;
    private final String title;
    private final  String author;
    private final int publicationYear;
    private final long branchId;
    private boolean isAvailable;

    private Book(BookBuilder builder) {
        this.bookId = builder.bookId;
        this.isbn = builder.isbn;
        this.title = builder.title;
        this.author = builder.author;
        this.publicationYear = builder.publicationYear;
        this.branchId = builder.branchId;
        this.isAvailable = true;
    }

    public long getBookId() {
        return bookId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return author;
    }


    public int getPublicationYear() {
        return publicationYear;
    }

    public long getBranchId() {
        return branchId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }


    public static class BookBuilder {
        private long bookId;
        private String isbn;
        private String title;
        private  String author;
        private int publicationYear;
        private long branchId;
        private boolean isAvailable;

        public BookBuilder bookId(long bookId) {
            this.bookId = bookId;
            return this;
        }

        public BookBuilder isbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder title(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder author(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder publicationYear(int year) {
            this.publicationYear = year;
            return this;
        }

        public BookBuilder branchId(long branchId) {
            this.branchId = bookId;
            return this;
        }

        public Book build() {
            return new Book(this);
        }

    }
}
