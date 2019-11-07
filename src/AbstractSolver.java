import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public abstract class AbstractSolver {

    protected int sizeOfBoard;

    protected int[] rows;

    protected int[][] conflictsBoard;

    protected Random random;

    public AbstractSolver(int sizeOfBoard) {
        this.sizeOfBoard = sizeOfBoard;
        rows = new int[sizeOfBoard];
        conflictsBoard = new int[sizeOfBoard][sizeOfBoard];
        random = new Random();
        initializeBoard();
    }

    protected abstract void solve();

    protected void initializeBoard() {
        rows = new int[rows.length];
        rows[0] = random.nextInt(rows.length);

        for (int j = 1; j < rows.length; j++) {
            int minConflictsForCurrentColumn = getConflictsFromRowCol(0, j, j + 1);
            ArrayList<Integer> cellsWithMinConflicts = new ArrayList<>();
            for (int i = 1; i < rows.length; i++) {
                rows[j] = i;
                int conflicts = getConflictsFromRowCol(i, j, j + 1);
                if (conflicts == minConflictsForCurrentColumn) {
                    cellsWithMinConflicts.add(i);
                } else if (conflicts < minConflictsForCurrentColumn) {
                    minConflictsForCurrentColumn = conflicts;
                    cellsWithMinConflicts.clear();
                    cellsWithMinConflicts.add(i);
                } else if (cellsWithMinConflicts.isEmpty()) {
                    cellsWithMinConflicts.add(i);
                }
            }

            int minConflictRow = cellsWithMinConflicts.get(random.nextInt(cellsWithMinConflicts.size()));
            rows[j] = minConflictRow;
        }
    }

    protected int getConflictsFromRowCol(int row, int col, int bound) {
        int count = 0;
        for (int c = 0; c < bound; c++) {
            if (c == col) continue;
            int r = rows[c];
            if (r == row || Math.abs(r - row) == Math.abs(c - col)) count++;
        }
        return count;
    }

    protected void printBoard(PrintStream stream) {
        for (int r = 0; r < sizeOfBoard; r++) {
            for (int c = 0; c < sizeOfBoard; c++) {
                stream.print(rows[c] == r ? "* " : "_ ");
            }
            stream.println();
        }
    }
}
