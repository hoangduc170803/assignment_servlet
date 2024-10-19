package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.Book;
import Model.Category;

public class BooksDAO extends DBContext {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        try {
            String query = "SELECT * FROM Books";
            try (PreparedStatement statement = getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("book_id");
                    String title = resultSet.getString("title");
                    String isbn = resultSet.getString("isbn");
                    int publisherId = resultSet.getInt("publisher_id");
                    int publicationYear = resultSet.getInt("publication_year");
                    int categoryId = resultSet.getInt("category_id");
                    int totalCopies = resultSet.getInt("total_copies");
                    int availableCopies = resultSet.getInt("available_copies");

                    Book book = new Book(id, title, isbn, publisherId, publicationYear, categoryId, totalCopies,
                            availableCopies);
                    books.add(book);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public void addBook(Book book) {
        try {
            String query = "INSERT INTO Books (title, isbn, publisher_id, publication_year, category_id, total_copies, available_copies) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getIsbn());
                statement.setInt(3, book.getPublisherId());
                statement.setInt(4, book.getPublicationYear());
                statement.setInt(5, book.getCategoryId());
                statement.setInt(6, book.getTotalCopies());
                statement.setInt(7, book.getAvailableCopies());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();

        try {
            String query;
            if (categoryId == 0) {
                // Fetch all books without category filter
                query = "SELECT * FROM Books";
            } else {
                // Fetch books by specific category
                query = "SELECT * FROM Books WHERE category_id = ?";
            }

            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                if (categoryId != 0) {
                    statement.setInt(1, categoryId);
                }

                try (ResultSet resultSet = statement.executeQuery()) {

                    while (resultSet.next()) {
                        int id = resultSet.getInt("book_id");
                        String title = resultSet.getString("title");
                        String isbn = resultSet.getString("isbn");
                        int publisherId = resultSet.getInt("publisher_id");
                        int publicationYear = resultSet.getInt("publication_year");
                        int totalCopies = resultSet.getInt("total_copies");
                        int availableCopies = resultSet.getInt("available_copies");

                        Book book = new Book(id, title, isbn, publisherId, publicationYear, categoryId, totalCopies,
                                availableCopies);
                        books.add(book);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByTitleAndCategory(String searchTerm, int categoryId) {
        List<Book> books = new ArrayList<>();

        try {
            String query;
            if (categoryId == 0) {
                // Fetch all books without category filter
                query = "SELECT * FROM Books WHERE title LIKE ?";
                try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                    statement.setString(1, "%" + searchTerm + "%");
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("book_id");
                            String title = resultSet.getString("title");
                            String isbn = resultSet.getString("isbn");
                            int publisherId = resultSet.getInt("publisher_id");
                            int publicationYear = resultSet.getInt("publication_year");
                            int category_id = resultSet.getInt("category_id");
                            int total_copies = resultSet.getInt("total_copies");
                            int available_copies = resultSet.getInt("available_copies");

                            Book book = new Book(id, title, isbn, publisherId, publicationYear, category_id,
                                    total_copies, available_copies);
                            books.add(book);
                        }
                    }
                }
            } else {
                // Fetch books by specific category
                query = "SELECT * FROM Books WHERE title LIKE ? AND category_id = ?";
                try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                    statement.setString(1, "%" + searchTerm + "%");
                    statement.setInt(2, categoryId);
                    try (ResultSet resultSet = statement.executeQuery()) {
                        while (resultSet.next()) {
                            int id = resultSet.getInt("book_id");
                            String title = resultSet.getString("title");
                            String isbn = resultSet.getString("isbn");
                            int publisherId = resultSet.getInt("publisher_id");
                            int publicationYear = resultSet.getInt("publication_year");
                            int category_id = resultSet.getInt("category_id");
                            int total_copies = resultSet.getInt("total_copies");
                            int available_copies = resultSet.getInt("available_copies");

                            Book book = new Book(id, title, isbn, publisherId, publicationYear, category_id,
                                    total_copies, available_copies);
                            books.add(book);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByTitle(String searchTerm) {
        List<Book> books = new ArrayList<>();

        try {
            String query = "SELECT * FROM Books WHERE title LIKE ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, "%" + searchTerm + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("book_id");
                        String title = resultSet.getString("title");
                        String isbn = resultSet.getString("isbn");
                        int publisherId = resultSet.getInt("publisher_id");
                        int publicationYear = resultSet.getInt("publication_year");
                        int categoryId = resultSet.getInt("category_id");
                        int totalCopies = resultSet.getInt("total_copies");
                        int availableCopies = resultSet.getInt("available_copies");

                        Book book = new Book(id, title, isbn, publisherId, publicationYear, categoryId, totalCopies,
                                availableCopies);
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public String getCategoryNameById(int categoryId) {
        String categoryName = "";

        try {
            String query = "SELECT name FROM Categories WHERE category_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, categoryId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        categoryName = resultSet.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoryName;
    }

    public String getPublisherNameById(int publisherId) {
        String publisherName = "";

        try {
            String query = "SELECT name FROM Publishers WHERE publisher_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, publisherId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        publisherName = resultSet.getString("name");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publisherName;
    }

    public List<Category> getAllCategoriesByName() {
        List<Category> categories = new ArrayList<>();

        try {
            String query = "SELECT * FROM Categories";
            try (PreparedStatement statement = getConnection().prepareStatement(query); ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("category_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");

                    Category category = new Category(id, name, description);
                    categories.add(category);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public List<Book> getBooksById(int bookId) {
        List<Book> books = new ArrayList<>();

        try {
            String query = "SELECT * FROM Books WHERE book_id = ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, bookId);
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("book_id");
                        String title = resultSet.getString("title");
                        String isbn = resultSet.getString("isbn");
                        int publisherId = resultSet.getInt("publisher_id");
                        int publicationYear = resultSet.getInt("publication_year");
                        int categoryId = resultSet.getInt("category_id");
                        int totalCopies = resultSet.getInt("total_copies");
                        int availableCopies = resultSet.getInt("available_copies");

                        Book book = new Book(id, title, isbn, publisherId, publicationYear, categoryId, totalCopies,
                                availableCopies);
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByIdOrTitle(int bookID, String title) {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM Books WHERE book_id = ? OR title LIKE ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setInt(1, bookID);
                statement.setString(2, "%" + title + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("book_id");
                        String bookTitle = resultSet.getString("title");
                        String isbn = resultSet.getString("isbn");
                        int publisherId = resultSet.getInt("publisher_id");
                        int publicationYear = resultSet.getInt("publication_year");
                        int categoryId = resultSet.getInt("category_id");
                        int totalCopies = resultSet.getInt("total_copies");
                        int availableCopies = resultSet.getInt("available_copies");

                        Book book = new Book(id, bookTitle, isbn, publisherId, publicationYear, categoryId, totalCopies,
                                availableCopies);
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByPartialId(String partialId) {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM Books WHERE CAST(book_id AS VARCHAR) LIKE ?";
            try (PreparedStatement statement = getConnection().prepareStatement(query)) {
                statement.setString(1, "%" + partialId + "%");
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        int id = resultSet.getInt("book_id");
                        String title = resultSet.getString("title");
                        String isbn = resultSet.getString("isbn");
                        int publisherId = resultSet.getInt("publisher_id");
                        int publicationYear = resultSet.getInt("publication_year");
                        int categoryId = resultSet.getInt("category_id");
                        int totalCopies = resultSet.getInt("total_copies");
                        int availableCopies = resultSet.getInt("available_copies");

                        Book book = new Book(id, title, isbn, publisherId, publicationYear, categoryId, totalCopies, availableCopies);
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
public Book getBookById(int id) {
        Book book = null;
        try {
            String sql = "SELECT * FROM Books WHERE book_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book();
                book.setId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setIsbn(rs.getString("isbn"));
                book.setPublisherId(rs.getInt("publisher_id"));
                book.setPublicationYear(rs.getInt("publication_year"));
                book.setCategoryId(rs.getInt("category_id"));
                book.setTotalCopies(rs.getInt("total_copies"));
                book.setAvailableCopies(rs.getInt("available_copies"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }

public void updateBook(Book book) {
    try {
        String query = "UPDATE Books SET title = ?, isbn = ?, publisher_id = ?, publication_year = ?, category_id = ?, total_copies = ?, available_copies = ? WHERE id = ?";
        try (PreparedStatement statement = getConnection().prepareStatement(query)) {
            statement.setString(1, book.getTitle());
            statement.setString(2, book.getIsbn());
            statement.setInt(3, book.getPublisherId());
            statement.setInt(4, book.getPublicationYear());
            statement.setInt(5, book.getCategoryId());
            statement.setInt(6, book.getTotalCopies());
            statement.setInt(7, book.getAvailableCopies());
            statement.setInt(8, book.getId()); // Assuming book.getId() returns the ID of the book being updated
            statement.executeUpdate();
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
  
}

public boolean deleteBook(int id) {
        boolean success = false;
        String sql = "DELETE FROM Books WHERE book_id  = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                success = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Hoặc xử lý ngoại lệ một cách phù hợp, ví dụ ghi log
        }
        return success;
    }


}
