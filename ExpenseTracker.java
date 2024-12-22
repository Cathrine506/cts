import java.io.*;
import java.util.*;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";

    // Expense class to store expense details
    static class Expense {
        String category;
        String description;
        double amount;

        Expense(String category, String description, double amount) {
            this.category = category;
            this.description = description;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return category + " | " + description + " | $" + amount;
        }
    }

    private static List<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        loadExpenses();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nExpense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Delete Expense");
            System.out.println("4. View Expenses by Category");
            System.out.println("5. Save and Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addExpense(scanner);
                    break;
                case 2:
                    viewExpenses();
                    break;
                case 3:
                    deleteExpense(scanner);
                    break;
                case 4:
                    viewByCategory(scanner);
                    break;
                case 5:
                    saveExpenses();
                    System.out.println("Expenses saved. Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addExpense(Scanner scanner) {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        expenses.add(new Expense(category, description, amount));
        System.out.println("Expense added successfully.");
    }

    private static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.println("\nExpenses:");
        for (int i = 0; i < expenses.size(); i++) {
            System.out.println((i + 1) + ". " + expenses.get(i));
        }
    }

    private static void deleteExpense(Scanner scanner) {
        viewExpenses();
        if (expenses.isEmpty()) return;

        System.out.print("Enter the number of the expense to delete: ");
        int index = scanner.nextInt() - 1;

        if (index >= 0 && index < expenses.size()) {
            expenses.remove(index);
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Invalid number. Please try again.");
        }
    }

    private static void viewByCategory(Scanner scanner) {
        System.out.print("Enter category: ");
        String category = scanner.nextLine();

        System.out.println("\nExpenses in category: " + category);
        for (Expense expense : expenses) {
            if (expense.category.equalsIgnoreCase(category)) {
                System.out.println(expense);
            }
        }
    }

    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.category + "," + expense.description + "," + expense.amount);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String category = parts[0];
                    String description = parts[1];
                    double amount = Double.parseDouble(parts[2]);
                    expenses.add(new Expense(category, description, amount));
                }
            }
        } catch (FileNotFoundException e) {
            // File not found is fine; it just means no expenses saved yet.
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}

