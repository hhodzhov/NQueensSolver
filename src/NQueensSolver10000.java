import java.util.ArrayList;

public class NQueensSolver10000 extends AbstractSolver {

    public NQueensSolver10000(int sizeOfBoard, boolean fillConflictBoard) {
        super(sizeOfBoard, fillConflictBoard);
    }

    @Override
    protected void solve() {

        int moves = 0;
        ArrayList<Integer> candidates = new ArrayList<>();

        while (true) {
            int maxConflicts = findCandidatesWithMaxConflicts(candidates);

            if (maxConflicts == 0) {
                return;
            }

            int columnWithMaxConflicts = candidates.get(random.nextInt(candidates.size()));

            findCandidatesWithMinConflicts(candidates, columnWithMaxConflicts);

            if (!candidates.isEmpty()) {
                Integer bestQueenRow = candidates.get(random.nextInt(candidates.size()));
                int previousRowIndex = rows[columnWithMaxConflicts];
                rows[columnWithMaxConflicts] = bestQueenRow;
                updateConflictsBoard(columnWithMaxConflicts, previousRowIndex, bestQueenRow);

            }
            moves++;
            if (moves == rows.length * 2) {
                System.out.println("Restarted");
                initializeBoard();
                moves = 0;
            }
        }
    }

    private int findCandidatesWithMaxConflicts(ArrayList<Integer> candidates) {
        int maxConflicts = 0;
        candidates.clear();
        for (int c = 0; c < sizeOfBoard; c++) {
            int conflicts = conflictsBoard[rows[c]][c];
            if (conflicts == maxConflicts) {
                candidates.add(c);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                candidates.clear();
                candidates.add(c);
            }
        }
        return maxConflicts;
    }

    private void findCandidatesWithMinConflicts(ArrayList<Integer> candidates, int columnWithMaxConflicts) {
        candidates.clear();
        int minConflicts = sizeOfBoard;
        for (int row = 0; row < sizeOfBoard; row++) {
            int conflicts = conflictsBoard[row][columnWithMaxConflicts];
            if (conflicts == minConflicts) {
                candidates.add(row);
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                candidates.clear();
                candidates.add(row);
            }
        }
    }

    private void updateConflictsBoard(int columnWithMaxConflicts, int previousRowIndex, int newRowIndex) {
        //decrease conflicts from current position
        recalculateConflictsInNewRow(previousRowIndex, true);
        recalculateConflictsInDownLeftDiagonal(previousRowIndex, columnWithMaxConflicts, true);
        recalculateConflictsUpRightDiagonal(previousRowIndex, columnWithMaxConflicts, true);
        recalculateConflictsUpLeftDiagonal(previousRowIndex, columnWithMaxConflicts, true);
        recalculateConflictsInDownRightDiagonal(previousRowIndex, columnWithMaxConflicts, true);

        //updating conflicts in new position
        recalculateConflictsInNewRow(newRowIndex, false);
        recalculateConflictsInDownLeftDiagonal(newRowIndex, columnWithMaxConflicts, false);
        recalculateConflictsUpRightDiagonal(newRowIndex, columnWithMaxConflicts, false);
        recalculateConflictsUpLeftDiagonal(newRowIndex, columnWithMaxConflicts, false);
        recalculateConflictsInDownRightDiagonal(newRowIndex, columnWithMaxConflicts, false);
    }

    private void recalculateConflictsInDownRightDiagonal(int row, int col, boolean decreaseConflictsByOne) {
        int size = rows.length;
        while (row < size - 1 && col < size - 1) {
            row++;
            col++;
            if (decreaseConflictsByOne) {
                conflictsBoard[row][col] -= 1;
            } else {
                conflictsBoard[row][col] = getConflictsFromRowCol(row, col, size);
            }
        }
    }

    private void recalculateConflictsUpLeftDiagonal(int row, int col, boolean decreaseConflictsByOne) {
        int size = rows.length;
        while (row >= 1 && col >= 1) {
            row--;
            col--;
            if (decreaseConflictsByOne) {
                conflictsBoard[row][col] -= 1;
            } else {
                conflictsBoard[row][col] = getConflictsFromRowCol(row, col, size);
            }
        }
    }

    private void recalculateConflictsUpRightDiagonal(int row, int col, boolean decreaseConflictsByOne) {
        int size = rows.length;
        while (row >= 1 && col < size - 1) {
            row--;
            col++;
            if (decreaseConflictsByOne) {
                conflictsBoard[row][col] -= 1;
            } else {
                conflictsBoard[row][col] = getConflictsFromRowCol(row, col, size);
            }
        }
    }

    private void recalculateConflictsInDownLeftDiagonal(int row, int col, boolean decreaseConflictsByOne) {
        int size = rows.length;
        while (row < size - 1 && col >= 1) {
            row++;
            col--;
            if (decreaseConflictsByOne) {
                conflictsBoard[row][col] -= 1;
            } else {
                conflictsBoard[row][col] = getConflictsFromRowCol(row, col, size);
            }
        }
    }

    private void recalculateConflictsInNewRow(int newRowIndex, boolean decreaseConflictsByOne) {
        for (int col = 0; col < rows.length; col++) {
            if (decreaseConflictsByOne) {
                conflictsBoard[newRowIndex][col] -= 1;
            } else {
                conflictsBoard[newRowIndex][col] = getConflictsFromRowCol(newRowIndex, col, rows.length);
            }
        }
    }
}
