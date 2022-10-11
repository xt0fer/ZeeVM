package rocks.zipcode;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ZeeCommand {
    START("start"),
    NOP(""),
    ERR("err"),
    ADD("add"),
    SUB("subtract"),
    HALT("halt"),
    PRINT("print"),
    PUSH("push"),
    // etc...
    ;

    private String name;

    private static final Map<String,ZeeCommand> ENUM_MAP;

    ZeeCommand (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    // Build an immutable map of String name to enum pairs.
    // Any Map impl can be used.

    static {
        Map<String,ZeeCommand> map = new ConcurrentHashMap<String, ZeeCommand>();
        for (ZeeCommand instance : ZeeCommand.values()) {
            map.put(instance.getName().toLowerCase(),instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static ZeeCommand get (String name) {
        return ENUM_MAP.getOrDefault(name.toLowerCase(), ZeeCommand.ERR);
    }

    // public static boolean isBinaryOp(Command op) {
    //     if (op == ADD ||
    //         op == SUB ||
    //         op == MUL ||
    //         op == DIV ||
    //         op == MOD ||
    //         op == POWER) 
    //     {
    //         return true;
    //     }
    //     return false;
    // }
}