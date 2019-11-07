import java.util.ArrayList;

public class NQueensSolver1000 extends AbstractSolver {

    public NQueensSolver1000(int sizeOfBoard, boolean fillConflictBoard) {
        super(sizeOfBoard, fillConflictBoard);
    }

    @Override
    protected void solve() {
        int moves = 0;
        ArrayList<Integer> candidates = new ArrayList<>();

        boolean found = false;
        while (!found) {
            int maxConflicts = findCandidatesWithMaxConflicts(candidates);
            if (maxConflicts == 0) {
                found = true;
                continue;
            }

            int columnWithMaxConflicts = candidates.get(random.nextInt(candidates.size()));
            findCandidatesWithMinConflicts(candidates, columnWithMaxConflicts);

            if (!candidates.isEmpty()) {
                rows[columnWithMaxConflicts] = candidates.get(random.nextInt(candidates.size()));
            }

            moves++;
            if (moves == sizeOfBoard * 2) {
                initializeBoard();
                moves = 0;
            }
        }
    }

    private int findCandidatesWithMaxConflicts(ArrayList<Integer> candidates) {
        candidates.clear();
        int maxConflicts = 0;

        for (int c = 0; c < sizeOfBoard; c++) {
            int conflicts = getConflictsFromRowCol(rows[c], c, sizeOfBoard);
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
        for (int r = 0; r < sizeOfBoard; r++) {
            int conflicts = getConflictsFromRowCol(r, columnWithMaxConflicts, sizeOfBoard);
            if (conflicts == minConflicts) {
                candidates.add(r);
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                candidates.clear();
                candidates.add(r);
            }
        }
    }
}
