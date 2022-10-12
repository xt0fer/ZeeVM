package rocks.zipcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Each "instruction" (zee operation) has an execute method
 * which get called whenever op is found in code.
 * This uses static (class) variable for the Print Buffer to
 * communicate with outside world.
 * Think of this class/enum as the "CPU" of the ZeeVM
 */
public enum ZeeOp {
    START("start"){
        public void execute(String[] args) {
            operandStack.clear();
        }
    },
    HALT("halt"){
        public void execute(String[] args) {
        }
    },
    NOP(""){
        public void execute(String[] args) {
        }
    },
    ERR("err"){
        public void execute(String[] args) {
            String p = "ZeeVM ERROR: unknown instruction";
            ZeeOp.printBuffer = p;
        }
    },
    ADD("add"){
        public void execute(String[] args) {
            operandStack.push(operandStack.pop() + operandStack.pop());
        }
    },
    SUB("subtract"){
        public void execute(String[] args) {
            int second = operandStack.pop();
            operandStack.push(operandStack.pop() - second);
        }
    },
    PRINT("print"){
        public void execute(String[] args) {
                String p = operandStack.pop().toString();
                ZeeOp.printBuffer = p;
        }
    },
    PUSH("push"){
        public void execute(String[] args) {
            // in this Op, args[1] will be like "#4",
            // need to strip "# and parse to Integer"
            int arg = Integer.parseInt(args[1].substring(1, args[1].length()));
            operandStack.push(arg);
        }
    },
    // etc...
    ;

    abstract void execute(String[] args);

    private String name;

    private static final Map<String,ZeeOp> ENUM_MAP;
    private static final Stack operandStack;
    public static String printBuffer;

    ZeeOp (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,ZeeOp> map = new ConcurrentHashMap<String, ZeeOp>();
        for (ZeeOp instance : ZeeOp.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
        operandStack = new Stack();
        printBuffer = "";
    }

    public static ZeeOp get (String name) {
        return ENUM_MAP.getOrDefault(name.toLowerCase(), ZeeOp.ERR);
    }

}

// Used inside of ZeeOp.
class Stack {
    ArrayList<Integer> stack = new ArrayList<>();
    void clear() { stack.clear(); }
    void push(Integer n) {
        //System.err.println("push "+n);
        stack.add(n);
    }
    Integer pop() {
            try {
                if (stack.size() < 1) throw new Exception("ZeeVM Stack Underflow");
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        int n = stack.remove(stack.size() - 1);
        //System.err.println("pop "+n);
        return n;
    }
}

