import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        int n = scanner.nextInt();

        //solver for 1000x1000
//        solve1000();
//        solver1000.printBoard(System.out);

        //solver for 10 000 x 10 000
        solve10000();
    }

    private static void solve10000() {
        AbstractSolver solver10000 = new NQueensSolver10000(1000, true);
        long start = System.currentTimeMillis();
        solver10000.solve();
        long stop = System.currentTimeMillis();
        System.out.println("Found in " + ((double) (stop - start)) / 1000 + "s.");
    }

    private static void solve1000() {
        AbstractSolver solver1000 = new NQueensSolver1000(1000, false);
        long start = System.currentTimeMillis();
        solver1000.solve();
        long stop = System.currentTimeMillis();
        System.out.println("Found in " + ((double) (stop - start)) / 1000 + "s.");
    }
}
