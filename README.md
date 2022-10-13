# ZeeVM
a Very simple virtual machine.

Three classes. 

### ZeeVM

this is where `main` is. class has two responsibilies:

- read in source code, and drop all lines which are comments
  - comment lines start with ";;" or "//"
- create the ZeeEngine and start it on the modified source
- print out the machines last print buffer

### ZeeEngine

this class simply steps thru the source, interpreting each line.
it looks for Errors and Prints and performs them.
It also looks for HALTs and stops the program when that is found.

### ZeeOp

this class, well, it's where all the magic happens. I should put together an
entire 30 min slide show to discuss the mechanics.
Notice how the class uses a static for the one and only operandStack.
And how the print buffer is done the same way.

Also, notice how the ENUM uses the `execute` method for each operation to manipulate
the internal state of the "stack virtual machine". Error handling is also easy: if an unknown word ends up in the input, the ERR op will happen and the interpreter will quit.

There is also a hashmap of strings to operations, so that the string input from the source gets mapped to the operation code without a bit hairy set of IFs or a SWITCH statement.

### What do I do?

Just like in https://github.com/xt0fer/Snowman, your job is to add `multiply`, `divide` and `mod` to the VM so it can handle code generated from your modified `snowman` compiler.

Make it so that anything you add to `snowman` gets supported here in `ZeeVM`.

*Java's VM and compiler, while a **Lot More** complicated, is pretty much like these two projects.*

## ZeeVM Instructions

The VM has a single stack of Integers (32 bit signed).

The instructions of the VM.

- `START` - clears the stack of the VM
- `HALT` - stops the VM
- `NOP` - does nothing
- `PRINT` - pops the top of the stack, converts the int into a string, and prints it on std out.

- `PUSH #n` - pushes the integer after the # sign onto the stack
- `ADD` - pops the top 2 integers, adds them, and pushes the sum back onto the stack
- `SUB` - pops the top 2 integers and substracts the first one from the second
  - (as in:  x<-pop(), y<-pop(), push(y-x). this is because the source was (SUBTRACT y x) )


### Confused about #4 ?

*why are numbers to be pushed formatted like "#3" and not just "3"??*

Well, it is common in assembly languages for **immediate** values (integer literals),
to be prefix'd with some char.
That way, in the future, you could use regular literals to refer to a number register
within the CPU. So, `PUSH 5` might mean *push the contents of register 5 to the stack*.
But `PUSH #5` means *push the literal number 5 to the stack*.
You could also then make memory address literals special by prefixing an @. (as in a memory location like "@00004F7f")
When you write the labs, you can make arbitrary design choices like this. :-)

## Some extensions

You cannot add these two extensions using `interface` and `implements`. The extension
needs to use the stack within the enum. So you just have to add the ops and the code to the ZeeOp ENUM.

## Adding labels

labelmap is a hashtable <string, integer>

`LABEL name` - declares a label, using the line position in the file. store in labelmap
`JUMP name` - sets the PC to the line postion of labelmap(name)+1, execution continues from there.

And maybe add these to label support?

`IFTRUE name` - pops TOS and jumps to name if the value is non-zero
`IFFALSE name` - pops TOS and jumps to name is value is zero

the labelmap has to be created before the start. the source needs to be scanned for "LABEL name" lines, and the line number of the label need to be inserted into the labelmap.

jumping then becomes looking up the labelname and getting the line number. then the PC needs to be set so that the next instruction read and interpreted is the one right after the label.

## Adding variables

variablemap is a hash table <string, intger>

`STORE name` - pop from stack and store in variablemap as <name, value>
`LOAD name` - gets variable name from variablemap and pushes it to stack

so if you wanted to do something line `(VAR X 5)` in Snowman, you might generate code that
ZeeVm would run like this:

```
        PUSH #5
        STORE X
```

and if later you wanted to `(ADD X 7)`, you would generate code that

```
        LOAD X
        PUSH #7
        ADD
```

and of course, if you wanted to do `(VAR X (ADD X 7))`, you would gen code like

```
    LOAD X
    PUSH #7
    ADD
    STORE X
```
