import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class NQueensSolver {
    private int sizeOfBoard;

    private int[] cols;

    private int[] rows;

    private int[] mainDiagonals;

    private int[] secondaryDiagonals;

    private Random random;

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
        //place first queen in random row of first col
        cols[0] = random.nextInt(sizeOfBoard);
        //increment conflicts in its in queen's row
        rows[cols[0]]++;
        //find the main diagonal of the cell
        int positionOfMainDiagonal = (0 - cols[0]) + sizeOfBoard - 1;
        mainDiagonals[positionOfMainDiagonal]++;
        //find the secondary diagonal of the cell
        int positionOfSecondaryDiagonal = cols[0];
        secondaryDiagonals[positionOfSecondaryDiagonal]++;

        for (int column = 1; column < sizeOfBoard; column++) {
            ArrayList<Integer> cellsWithMinConflicts = new ArrayList<>();
            int minConflictsForCurrentColumn = getConflictsFromRowCol(0, column, false);
            cellsWithMinConflicts.add(0);
            for (int row = 1; row < sizeOfBoard; row++) {
                int conflicts = getConflictsFromRowCol(row, column, false);
                minConflictsForCurrentColumn = getMinConflicts(cellsWithMinConflicts,
                        minConflictsForCurrentColumn, row, conflicts);
            }

            int minConflictRow = cellsWithMinConflicts.get(random.nextInt(cellsWithMinConflicts.size()));

            //in current column and in the minConfictRow
            cols[column] = minConflictRow;

            //increase conflicts in queen's row
            rows[minConflictRow]++;
            //increase conflicts in queen's main diagonal
            mainDiagonals[(column - minConflictRow) + sizeOfBoard - 1]++;
            //increase conflicts in queen's secondary diagonal
            secondaryDiagonals[column + minConflictRow]++;
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

            findQueenRowWithMinConflicts(conflictCandidates, columnWithMaxConflicts);

            if (!conflictCandidates.isEmpty()) {
                Integer bestQueenRow = conflictCandidates.get(random.nextInt(conflictCandidates.size()));

                int previousRowIndex = cols[columnWithMaxConflicts];
                cols[columnWithMaxConflicts] = bestQueenRow;

                updateConflictsFromRowCol(previousRowIndex, columnWithMaxConflicts, Action.DECREMENT_BY_ONE);

                updateConflictsFromRowCol(bestQueenRow, columnWithMaxConflicts, Action.INCREMENT_BY_ONE);
//                System.out.println("Current board");
//                printBoard(System.out);
            }

            if (steps == sizeOfBoard * sizeOfBoard) {
                System.out.println("Restarted");
                initializeBoard();
                steps = 0;
            }
        }
    }

    public void printBoard(PrintStream stream) {
        for (int row = 0; row < sizeOfBoard; row++) {
            for (int column = 0; column < sizeOfBoard; column++) {
                stream.print(cols[column] == row ? "* " : "_ ");
            }
            stream.println();
        }
    }

    private int getMinConflicts(ArrayList<Integer> cellsWithMinConflicts, int minConflicts, int row,
                                int conflicts) {
        if (conflicts == minConflicts) {
            cellsWithMinConflicts.add(row);
        } else if (conflicts < minConflicts) {
            minConflicts = conflicts;
            cellsWithMinConflicts.clear();
            cellsWithMinConflicts.add(row);
        }
        return minConflicts;
    }

    private int getConflictsFromRowCol(int row, int col, boolean fromQueenPosition) {
        int conflicts = 0;
        conflicts += rows[row];
        conflicts += mainDiagonals[(col - row) + sizeOfBoard - 1];
        conflicts += secondaryDiagonals[row + col];

        //when we want to calculate the conflicts of a queen then we shouldn't count it's own conflict from it's position
        if (fromQueenPosition) {
            //decrement with 3 because of the 3 increments above
            conflicts -= 3;
        }
        return conflicts;
    }

    private void updateConflictsFromRowCol(int row, int col, Action action) {
        switch (action) {
            case INCREMENT_BY_ONE:
                rows[row]++;
                mainDiagonals[(col - row) + sizeOfBoard - 1]++;
                secondaryDiagonals[row + col]++;
                break;
            case DECREMENT_BY_ONE:
                rows[row]--;
                mainDiagonals[(col - row) + sizeOfBoard - 1]--;
                secondaryDiagonals[row + col]--;
                break;
        }
    }

    private void findQueenRowWithMinConflicts(ArrayList<Integer> conflictCandidates, int columnWithMaxConflicts) {
        conflictCandidates.clear();
        int minConflicts = sizeOfBoard;
        for (int row = 0; row < sizeOfBoard; row++) {
            //don't check the current position of the queen, so continue with next iteration
            if (row == cols[columnWithMaxConflicts]) {
                continue;
            }
            int conflicts = getConflictsFromRowCol(row, columnWithMaxConflicts, false);
            minConflicts = getMinConflicts(conflictCandidates, minConflicts, row, conflicts);
        }
    }

    //get the columns with max conflicts
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
