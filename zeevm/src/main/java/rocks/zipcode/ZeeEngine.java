package rocks.zipcode;

public class ZeeEngine {
    private Boolean _DEBUG = false;

    public String interpret(String lines) {
        ZeeOp op;
        String[] source = lines.split("\n");
        
        ZeeOp.registerLabels(source);
        ZeeOp.programCounter = 0;

        while (ZeeOp.programCounter < source.length) {
            String line = source[ZeeOp.programCounter];

            if (_DEBUG) System.err.printf("%d: %s\n", ZeeOp.programCounter, line);

            ZeeOp.setPC(ZeeOp.programCounter + 1);

            op = this.interpretInstruction(line);
            if (op == ZeeOp.HALT) break;
            if (op == ZeeOp.PRINT) 
                System.out.println(ZeeOp.printBuffer);
            if (op == ZeeOp.ERR) {
                System.out.println(ZeeOp.printBuffer);
                break; // halt on error.
            }
        }
        return ZeeOp.printBuffer;
    }

    /**
     * perform the line of code
     * @param line
     */
    private ZeeOp interpretInstruction(String line) {
        String[] tokens = line.split(" ");
        ZeeOp op = ZeeOp.get(tokens[0]);
        
        op.execute(tokens);
 
        return op;
    }
}
