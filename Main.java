package LibraryManagementSystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n1. Add Book");
            System.out.println("2. Check Fine");
            System.out.println("3. Update Book");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("Year: ");
                    int year = sc.nextInt();
                    sc.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    System.out.print("Username: ");
                    String username = sc.nextLine();

                    Book book = new Book(title, author, year, isbn, username);
                    BookManager.addBook(book);
                    break;

                case 2:
                    System.out.print("Enter username: ");
                    username = sc.nextLine();
                    BookManager.calculateFine(username);
                    break;

                case 3:
                    System.out.print("Enter book ID to update: ");
                    int bookId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter new Title (leave blank to skip): ");
                    String newTitle = sc.nextLine();
                    System.out.print("Enter new ID (or -1 to skip): ");
                    int newId = sc.nextInt();
                    sc.nextLine();
                    BookManager.updateBook(bookId, newTitle, newId);
                    break;

                case 4:
                    System.out.print("Enter your username: ");
                    username = sc.nextLine();
                    System.out.print("Enter ISBN of book: ");
                    isbn = sc.nextLine();
                    BookManager.borrowBook(username, isbn);
                    break;

                case 5:
                    System.out.print("Enter your username: ");
                    username = sc.nextLine();
                    System.out.print("Enter ISBN of book: ");
                    isbn = sc.nextLine();
                    BookManager.returnBook(username, isbn);
                    break;

                case 6:
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } while (choice != 6);

        sc.close();
    }
}
