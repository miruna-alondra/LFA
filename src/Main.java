import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static Stack<BigInteger> stack = new Stack<>();
    public static Interpreter interpreter;
    public static Scanner scStdin = new Scanner(System.in);
    public static String readLine;
    public static Stack<Integer> braces = new Stack<>();
    public static int base = 10;

    public static void main(String[] args) throws Exception
    {
       File file =
               new File(args[0]);
       if (args.length > 1) {
           base = Integer.parseInt(args[1]);
       }
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            readLine = sc.nextLine();
            interpreter = new Interpreter(readLine);
        }
        if (readLine.length() % 4 == 0) {
            interpreter.split();
            interpreter.getStrings();
            for (int i = 0; i < interpreter.commands.size(); i++) {
                interpreter.interpreter(interpreter.commands.get(i), i);
            }

        } else {
            System.err.println("Error:" + (readLine.length() / 4));
            System.exit(-1);
        }
        if (!braces.isEmpty()) {
            System.err.println("Error:" + (readLine.length() / 4));
            System.exit(-1);
        }
    }
}
