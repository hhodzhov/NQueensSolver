import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        int n = scanner.nextInt();

        AbstractSolver solver1000 = new NQueensSolver1000(1000);
        long start = System.currentTimeMillis();
        solver1000.solve();
        long stop = System.currentTimeMillis();
        System.out.println("Found in " + ((double) (stop - start)) / 1000 + "s.");
//        solver1000.printBoard(System.out);
    }
}
