# library_management_system
Book
Attributes:
    -bookId: long {final}
    -isbn: String {final}
    -title: String {final}
    -author: String {final}
    -publicationYear: int {final}
    -branchId: long {final}
    -isAvailable: boolean
Methods:
    +getBookId(): long
    +getIsbn(): String
    +getTitle(): String
    +getAuthor(): String
    +getGenre(): String
    +getPublicationYear(): int
    +getBranchId(): long
    +isAvailable(): boolean
    +setAvailable(available: boolean): void
Inner class:
    BookBuilder :
        created builder design pattern for book object creation. Immutable fields except
        isAvailable
Patron:
    Attributes:
        -id: long {final}
        -name: String
        -contact: String
        -borrowingHistory: List<String> {final}
    Methods:
        +getId(): long
        +getName(): String
        +getContact(): String
        +setName(name: String): void
        +setContact(contact: String): void
        +getBorrowingHistory(): List<String>
        +addToBorrowingHistory(isbn: String): void
    PatronBuilder:
        created builder design pattern for patron object creation.
Branch:
    Attributes:
        -branchId: long {final}
        -books: Map<String, Book> {final}
        -patrons: Map<Long, Patron> {final}
        -reservations: Map<String, Reservation> {final}
    Methods:
        +Branch(branchId: long)
        +addBook(book: Book): void
        +removeBook(isbn: String): void
        +addPatron(patron: Patron): void
        +updatePatron(id: long, name: String, contact: String): void
        +searchByTitle(title: String): List<Book>
        +searchByAuthor(author: String): List<Book>
        +searchByIsbn(isbn: String): Book
        +checkoutBook(isbn: String, patronId: long): void
        +returnBook(isbn: String): void
        +reserveBook(isbn: String, patronId: long): void
        +getAvailableBooks(): List<Book>
        +getBranchId(): long
        +getBooks(): Map<String, Book>
        +getPatrons(): Map<Long, Patron>
        +getReservations(): Map<String, Reservation>
    Implements Searchable and Reservable interfaces. Manages books, patrons, and reservations.
Reservation:
    Attributes:
        -bookIsbn: String {final}
        -patronQueue: Queue<Long> {final}
    Methods:
        +Reservation(bookIsbn: String)
        +addPatron(patronId: long): void
        +getNextPatron(): Long
        +hasReservations(): boolean
        +getBookIsbn(): String

LibraryManagementSystem:
    Attributes:
        -branches: Map<Long, Branch> {final}
        -recommendationStrategy: RecommendationStrategy
    Methods:
        +LibraryManagementSystem()
        +addBranch(branchId: long): void
        +transferBook(isbn: String, fromBranchId: long, toBranchId: long): boolean
        +getRecommendations(patronId: long, branchId: long): List<Book>
        +setRecommendationStrategy(strategy: RecommendationStrategy): void
        +getBranches(): Map<Long, Branch>
        +getRecommendationStrategy(): RecommendationStrategy
HistoryBasedRecommendation:
    Methods:
        +recommendBooks(patron: Patron, books: Map<String, Book>): List<Book>

Searchable (Interface)
    Methods:
        +searchByTitle(title: String): List<Book>
        +searchByAuthor(author: String): List<Book>
        +searchByIsbn(isbn: String): Book

Reservable (Interface)
    Methods:
        +reserveBook(isbn: String, patronId: long): void

RecommendationStrategy (Interface)
    Methods:
    +recommendBooks(patron: Patron, books: Map<String, Book>): List<Book>

Relationships
Composition:
    Branch Map<String, Book>: Branch owns a collection of books.
    Branch  Map<Long, Patron>: Branch owns a collection of patrons.
    Branch Map<String, Reservation>: Branch owns reservations.
    LibraryManagementSystem  Map<Long, Branch>: System owns branches.

Association:
    Reservation —— Patron: Reservation references patron IDs in its queue.
    Patron —— Book: Patron’s borrowing history references book ISBNs.


Design patterns:
    Builder Pattern:
        Book contains BookBuilder inner class 
        Patron contains PatronBuilder
        Represented by the inner class relationship and build() method returning the outer class.
    Strategy Pattern:
        RecommendationStrategy interface implemented by HistoryBasedRecommendation.
        LibraryManagementSystem has a recommendationStrategy attribute and setRecommendationStrategy method.
    Observer Pattern:
    Reservation manages a queue of patron IDs, notifying the next patron when a book is returned (via Branch.returnBook).
    Represented by the association between Reservation and Patron (patron IDs in queue).