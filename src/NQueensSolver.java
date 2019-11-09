import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class NQueensSolver {
    protected int sizeOfBoard;

    protected int[] cols;

    protected int[] rows;

    protected int[] mainDiagonals;

    protected int[] secondaryDiagonals;

    protected Random random;

    public NQueensSolver(int sizeOfBoard) {
        this.sizeOfBoard = sizeOfBoard;
        cols = new int[sizeOfBoard];
        rows = new int[sizeOfBoard];
        mainDiagonals = new int[2 * sizeOfBoard - 1];
        secondaryDiagonals = new int[2 * sizeOfBoard - 1];
        random = new Random();
        initializeBoard();
    }

    private void initializeBoard() {
        cols = new int[sizeOfBoard];
        //place first queen
        cols[0] = random.nextInt(sizeOfBoard);
        //increment queen size in its row
        rows[cols[0]]++;
        //find this cell to which main diagonal belongs
        int positionOfMainDiagonal = (0 - cols[0]) + sizeOfBoard - 1;
        mainDiagonals[positionOfMainDiagonal]++;
        //find this cell to which secondary diagonal belongs
        int positionOfSecondaryDiagonal = cols[0];
        secondaryDiagonals[positionOfSecondaryDiagonal]++;

        for (int j = 1; j < sizeOfBoard; j++) {
            int minConflictsForCurrentColumn = getConflictsFromRowCol(0, j, false);
            ArrayList<Integer> cellsWithMinConflicts = new ArrayList<>();
            for (int i = 1; i < sizeOfBoard; i++) {
                cols[j] = i;
                int conflicts = getConflictsFromRowCol(i, j, false);
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
            cols[j] = minConflictRow;

            rows[minConflictRow]++;
            mainDiagonals[(j - minConflictRow) + sizeOfBoard - 1]++;
            secondaryDiagonals[j + minConflictRow]++;
        }
//        System.out.println("Initial board");
//        printBoard(System.out);
    }

    public void solve() {
        int steps = 0;

        ArrayList<Integer> conflictCandidates = new ArrayList<>();

        while (true) {
            int maxConflicts = findQueensWithMaxConflicts(conflictCandidates);

            if (maxConflicts == 0) {
                return;
            }

            int columnWithMaxConflicts = conflictCandidates.get(random.nextInt(conflictCandidates.size()));

            findQueensWithMinConflicts(conflictCandidates, columnWithMaxConflicts);

            if (!conflictCandidates.isEmpty()) {
                Integer bestQueenRow = conflictCandidates.get(random.nextInt(conflictCandidates.size()));

                int previousRowIndex = cols[columnWithMaxConflicts];
                cols[columnWithMaxConflicts] = bestQueenRow;

                decrementConflictsFromPreviousPosition(previousRowIndex, columnWithMaxConflicts);

                incrementConflictsFromCurrentPosition(bestQueenRow, columnWithMaxConflicts);
//                System.out.println("Current board");
//                printBoard(System.out);
            }

//            steps++;
//            if (steps == sizeOfBoard * 2) {
//                System.out.println("Restarting...");
//                steps = 0;
//                initializeBoard();
//            }
        }
    }

    public void printBoard(PrintStream stream) {
        for (int r = 0; r < sizeOfBoard; r++) {
            for (int c = 0; c < sizeOfBoard; c++) {
                stream.print(cols[c] == r ? "* " : "_ ");
            }
            stream.println();
        }
    }

    private int getConflictsFromRowCol(int row, int col, boolean fromQueenPosition) {
        int conflicts = 0;
        conflicts += rows[row];
        conflicts += mainDiagonals[(col - row) + sizeOfBoard - 1];
        conflicts += secondaryDiagonals[row + col];

        if (fromQueenPosition) {
            conflicts -= 3;
        }

        return conflicts;
    }

    private void incrementConflictsFromCurrentPosition(int row, int col) {
        rows[row]++;
        mainDiagonals[(col - row) + sizeOfBoard - 1]++;
        secondaryDiagonals[row + col]++;
    }

    private void decrementConflictsFromPreviousPosition(int row, int col) {
        rows[row]--;
        mainDiagonals[(col - row) + sizeOfBoard - 1]--;
        secondaryDiagonals[row + col]--;
    }

    private void findQueensWithMinConflicts(ArrayList<Integer> conflictCandidates, int columnWithMaxConflicts) {
        conflictCandidates.clear();
        int minConflicts = sizeOfBoard;
        for (int row = 0; row < sizeOfBoard; row++) {
            //don't check the current position of the queen
            if (row == cols[columnWithMaxConflicts]) {
                continue;
            }
            int conflicts = getConflictsFromRowCol(row, columnWithMaxConflicts, false);
            if (conflicts == minConflicts) {
                conflictCandidates.add(row);
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                conflictCandidates.clear();
                conflictCandidates.add(row);
            }
        }
    }

    private int findQueensWithMaxConflicts(ArrayList<Integer> conflictCandidates) {
        int maxConflicts = 0;
        conflictCandidates.clear();
        for (int i = 0; i < sizeOfBoard; i++) {
            int conflicts = getConflictsFromRowCol(cols[i], i, true);
            if (conflicts == maxConflicts) {
                conflictCandidates.add(i);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                conflictCandidates.clear();
                conflictCandidates.add(i);
            }
        }
        return maxConflicts;
    }
}
