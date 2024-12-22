import java.util.*;

public class Main {
    private static final int GRID_SIZE = 4; // 4x4 grid
    private static String[][] grid;
    private static boolean[][] revealed;
    private static String[] icons = {"A", "A", "B", "B", "C", "C", "D", "D", 
                                     "E", "E", "F", "F", "G", "G", "H", "H"};

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Initialize the grid
        grid = new String[GRID_SIZE][GRID_SIZE];
        revealed = new boolean[GRID_SIZE][GRID_SIZE];
        initializeGrid();

        int moves = 0;
        int matches = 0;

        System.out.println("Welcome to the Memory Game!");
        System.out.println("Match all the pairs to win the game.\n");

        while (matches < (GRID_SIZE * GRID_SIZE) / 2) {
            printGrid();

            // Get the first tile
            System.out.print("Enter the row and column of the first tile (e.g., 1 2): ");
            int r1 = scanner.nextInt() - 1;
            int c1 = scanner.nextInt() - 1;

            if (!isValidMove(r1, c1)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            // Reveal the first tile
            revealed[r1][c1] = true;
            printGrid();

            // Get the second tile
            System.out.print("Enter the row and column of the second tile (e.g., 2 3): ");
            int r2 = scanner.nextInt() - 1;
            int c2 = scanner.nextInt() - 1;

            if (!isValidMove(r2, c2) || (r1 == r2 && c1 == c2)) {
                System.out.println("Invalid move. Try again.");
                revealed[r1][c1] = false;
                continue;
            }

            // Reveal the second tile
            revealed[r2][c2] = true;
            printGrid();

            // Check for a match
            if (grid[r1][c1].equals(grid[r2][c2])) {
                System.out.println("It's a match!");
                matches++;
            } else {
                System.out.println("Not a match.");
                revealed[r1][c1] = false;
                revealed[r2][c2] = false;
            }

            moves++;
            System.out.println("Moves: " + moves + "\n");
        }

        System.out.println("Congratulations! You matched all pairs in " + moves + " moves.");
        scanner.close();
    }

    private static void initializeGrid() {
        List<String> iconList = Arrays.asList(icons);
        Collections.shuffle(iconList);

        int index = 0;
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = iconList.get(index);
                revealed[i][j] = false;
                index++;
            }
        }
    }

    private static void printGrid() {
        System.out.println("  1 2 3 4");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < GRID_SIZE; j++) {
                if (revealed[i][j]) {
                    System.out.print(grid[i][j] + " ");
                } else {
                    System.out.print("? ");
                }
            }
            System.out.println();
        }
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !revealed[row][col];
    }
}
