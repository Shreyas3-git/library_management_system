package com.librarymng.library.contract;

public interface Reservable
{
    void reserveBook(String isbn, long patronId);
}
