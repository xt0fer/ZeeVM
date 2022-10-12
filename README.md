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

this class, well, it's where all the magic happens.