# ZeeVM
a Very simple virtual machine.

Three classes. 

### ZeeVM

this is where `main` is. class has two responsibilies:

- read in source code, and drop all lines which are comments
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

Just like in https://github.com/xt0fer/Snowman, your job is to add `multiply` and `divide` to the VM so it can handle code generated from your modified `snowman` compiler.

Make it so that anything you add to `snowman` gets supported here in `ZeeVM`.

*Java's VM, while a Lot More complicated, it pretty much like these two projects.*