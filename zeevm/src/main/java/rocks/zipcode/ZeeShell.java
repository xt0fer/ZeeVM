package rocks.zipcode;


// A REPL for ZeeVM

import java.util.Scanner;

public class ZeeShell {
    private static Boolean _DEBUG = false;

    public static void main(String[] args) {

        Scanner sn = new Scanner(System.in);
        String line;

        ZeeOp op;

        ZeeOp.initialize();
        System.out.println("\nZeeShell v1.0.");

        while (true) {
            System.out.print(">> ");
            line = sn.nextLine();

            if (line.isEmpty()) continue;
            // if line is comment, drop it.
            if (line.startsWith(";;")) continue; // handle two kinds of
            if (line.startsWith("//")) continue; // comments...

            if (_DEBUG) System.out.printf(">> [%d: %s]\n", ZeeOp.programCounter, line);

            ZeeOp.setPC(ZeeOp.programCounter + 1);

            op = interpretInstruction(line);
            if (op == ZeeOp.HALT) break;
            if (op == ZeeOp.PRINT) {
                System.out.println(ZeeOp.printBuffer);
            }
            if (op == ZeeOp.ERR) {
                System.out.println(ZeeOp.printBuffer);
                break; // halt on error.
            }
        }
        System.out.println("done.");
        System.exit(0);
    }

    private static ZeeOp interpretInstruction(String line) {
        String[] tokens = line.split(" ");
        ZeeOp op = ZeeOp.get(tokens[0]);

        op.execute(tokens);

        return op;
    }

}
