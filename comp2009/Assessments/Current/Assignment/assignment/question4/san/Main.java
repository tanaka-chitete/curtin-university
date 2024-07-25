public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: san <temperature> <step>");
        }
        else {
            Solver.solve(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        }
    }
}
