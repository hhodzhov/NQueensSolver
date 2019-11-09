import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//        int n = scanner.nextInt();

        long start = System.currentTimeMillis();
        NQueensSolver nQueensSolver = new NQueensSolver(10000);
        nQueensSolver.solve();
        long stop = System.currentTimeMillis();
        System.out.println("Found in " + ((double) (stop - start)) / 1000 + "s.");
//        nQueensSolver.printBoard(System.out);
    }
}
