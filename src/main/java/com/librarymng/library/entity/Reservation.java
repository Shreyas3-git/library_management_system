package com.librarymng.library.entity;

import java.util.LinkedList;
import java.util.Queue;

public class Reservation
{
    private final String bookIsbn;
    private final Queue<Long> patronQueue;

    public Reservation(String bookIsbn) {
        this.bookIsbn = bookIsbn;
        this.patronQueue = new LinkedList<>();
    }

    public void addPatron(long patronId) {
        this.patronQueue.add(patronId);
    }

    public Long getNextPatron() {
        return patronQueue.poll();
    }
    public boolean hasReservations() {
        return !patronQueue.isEmpty();
    }
    public String getBookIsbn() {
        return bookIsbn;
    }

}
