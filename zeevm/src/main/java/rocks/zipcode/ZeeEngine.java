package rocks.zipcode;

import java.util.ArrayList;

public class ZeeEngine {

    class Stack {
        ArrayList<Integer> stack = new ArrayList<>();
        void push(Integer n) {
            //System.err.println("push "+n);
            stack.add(n);
        }
        Integer pop() { // hmm. what's pop really??
            int n = stack.get(stack.size() - 1);
            stack.remove(stack.size() - 1);
            //System.err.println("pop "+n);
            return n;
        }
    }

    Stack stack = new Stack();
    String printBuffer = "";

    public String interpret(String lines) {
        ZeeCommand op;
        String[] source = lines.split("\n");

        for (String line : source) {
            op = this.interpretInstruction(line);
            if (op == ZeeCommand.HALT) break;
            if (op == ZeeCommand.PRINT) 
                System.out.println(this.printBuffer);
        }
        return this.printBuffer;
    }

    /**
     * perform the line of code
     * @param line
     */
    private ZeeCommand interpretInstruction(String line) {
        String[] tokens = line.split(" ");
        ZeeCommand op = ZeeCommand.get(tokens[0]);
        
        if (op == ZeeCommand.HALT) return op;
        if (op == ZeeCommand.START) return op;
        if (op == ZeeCommand.PRINT) {
            String p = stack.pop().toString();
            this.printBuffer = p;
            return op;
        }
        if (op == ZeeCommand.ADD) {
            stack.push(stack.pop() + stack.pop());
        }
        if (op == ZeeCommand.SUB) {
            int top = stack.pop();
            stack.push(stack.pop() - top);
        }
        if (op == ZeeCommand.PUSH) {
            int arg = Integer.parseInt(tokens[1].substring(1, tokens[1].length()));
            stack.push(arg);
        }

        return op;
    }


}
