# Part 1.

## Yet Another Debugger

*Info.PO.22/23*

*ver 1.0*

In this task, you are required to write a set of classes that represent instructions of a simple programming language called *Macchiato*. Programs in this language consist of a single block (see the description of instructions below).

The classes in your solution should allow executing programs. The result of executing a program should be the execution of individual instructions (as described below) and printing to the standard output the values of all variables from the main block of the program. In this task, **reading programs from text files is not required**. Sample Macchiato programs should be built within the Java code using the implemented classes in your solution.

Apart from the capability to execute programs, we are also interested in tracing the execution (i.e., implementing a simple debugger). Therefore, your solution should provide two options for executing a program:
- Execution without debugging - in this case, the program is executed from start to finish (unless a runtime error occurs, but even then, only an error message is printed, and the execution stops without invoking the debugger).
- Execution with debugging - in this case, the program is immediately halted (before executing the first instruction) and waits for commands inputted from the standard input.

All debugger commands are single characters. After the name of some commands (see below), there may be a (single or multiple) space followed by an integer.

Debugger command set:
- **c**(ontinue)
  - parameterless, resumes the execution of the program under debugging (until completion). If the program has already finished, the command prints an appropriate message (and does nothing else).
- **s**(tep) \<number\>
  - the program under debugging executes exactly \<number\> steps. By a step, we mean the execution of a single instruction (including any nested instructions). After executing the specified number of steps, the instruction (possibly compound) that is to be executed next is printed. If the program reaches its end before executing the specified number of instructions, only an appropriate message is printed, and the program ends normally.
- **d**(isplay) \<number\>
  - displays the current valuation. The parameter specifies how many levels of outer block variable valuations should be displayed. The command *d 0* means displaying the current valuation. If the provided number is greater than the level of nesting of the current program instruction, only an appropriate message is printed.
- **e**(xit)
  - terminates the program execution and exits the debugger. It does not print the final variable valuation.

The Macchiato language allows only variables with single-letter names (English alphabet letters from 'a' to 'z'). All variables are of type int (same as in Java).

Programs can contain the following instructions:
- Block
  - a block contains a sequence of variable declarations and a sequence of instructions. Each of these parts can be empty. Declarations placed within a block are visible from their end to the end of their block (and nowhere else). Local declarations may shadow outer declarations.
- For loop \<variable\> \<expression\> \<instructions\>
  - executes the instructions <value times>, at each iteration, \<instructions\> are executed in a block with \<variable\> taking consecutive values from the range 0..\<value of the expression\>-1. The value of the expression is evaluated only once, just before starting the loop execution (so even if the executed instructions change this value, the number of loop iterations and the variable value at the beginning of each iteration will not change). If the evaluated value of the expression is less than or equal to zero, the loop does not execute any iteration. An error in evaluating the expression interrupts further loop execution (the instructions in the loop will not be executed at all).
- Conditional instruction if \<expr1\> =|\<\>|\<|\>|\<=|\>= \<expr2\> then \<instructions\> else \<instructions\>
  - standard meaning,
  - first, we evaluate the first, then the second expression,
  - an error in evaluating the expression interrupts further execution of this instruction,
  - the else part \<instructions\> can be omitted.
- Assignment of a value to a variable \<name\> \<expr\>
  - assigns the variable a value equal to the evaluated value of the expression,
  - an error in evaluating the expression interrupts further execution of this instruction (i.e., in this case, the variable value remains unchanged),
  - it ends with an error if there is no visible variable with the given name at this point in the program.
- Printing the value of an expression print \<expr\>
  - the value of the expression is evaluated, and then it is printed on the standard output in the next line.
  - if evaluating the expression fails, this instruction prints nothing.

In blocks, there are declarations:
- Variable declaration \<name\> \<expr\>
  - introduces a variable into the current scope of visibility (associated with the block containing this declaration) and initializes it with the evaluated value of the expression,
  - within one block, you cannot declare two (or more) variables with the same name.
  - variable names from different blocks can be the same; in particular, variable shadowing may occur (instructions and expressions always see the variable declared in the most closely surrounding block).
  - variables can only be declared at the beginning of a block (i.e., within the variable declarations).

If an error occurs during the execution of an instruction, the program execution is interrupted, and a message containing the values of all variables visible in the block where the error occurred (these variables may be declared in this or in surrounding blocks) is printed, along with the instruction that directly caused the error.

In the Macchiato language, all expressions have integer values. An expression can have one of the following forms:
- Integer literal
  - the value of such an expression is the value of the literal. The syntax and range of literals are the same as in Java for the int type.
- Variable
  - the value of such an expression is the value of the variable visible at the given point in the program. If there is no visible variable with the given name at this point, the evaluation of the variable value ends with an error.
- Addition \<expr1\> \<expr2\>
  - the sum of two expressions, first, we evaluate the first, then the second expression, then we perform addition on the obtained values. When the result exceeds the range, the result is the same as in Java for the same values.
- Subtraction \<expr1\> \<expr2\>
  - the difference between two expressions, first, we evaluate the first, then the second expression, then we subtract the value of the second from the value of the first. When the result exceeds the range, the result is the same as in Java for the same values.
- Multiplication \<expr1\> \<expr2\>
  - the product of two expressions, first, we evaluate the first, then the second expression, then we perform multiplication on the obtained values. When the result exceeds the range, the result is the same as in Java for the same values.
- Division \<expr1\> \<expr2\>
  - integer division of two expressions, first, we evaluate the first, then the second expression, then we divide the value of the first by the value of the second. For negative numbers or numbers with different signs, the result is the same as in Java for the same values. The evaluation ends with an error when the value of the second expression is zero.
- Modulus \<expr1\> \<expr2\>
  - the remainder of the integer division of two expressions, first, we evaluate the first, then the second expression, then we divide the value of the first by the value of the second, and the result is the remainder. For negative numbers or numbers with different signs, the result is the same as in Java for the same values. The evaluation ends with an error when the value of the second expression is zero.

A sample program in the Macchiato language written in metasyntax (we remind you that **your solution should not read text of Macchiato programs**, so the specific syntax is irrelevant in this task):
```
begin block
  var n 30
  for k n-1
    begin block
      var p 1
      k := k+2
      for i k-2
        i := i+2
        if k % i = 0
          p := 0
      if p = 1
        print k
    end block
end block
```
As a solution, you should provide a set of packages and classes enabling the execution and tracing of programs in Macchiato, along with the above sample program in this language created within the main function.

Good luck!

# Part 2.

*Macchiato 1.1*

*Info.PO.22/23, v1.2*

In this task, you are required to implement a new version of the Macchiato language, consisting of the following new language features and enhancements in its ecosystem:

## 1. Procedures
Macchiato version 1.1 introduces procedures. Procedures can be thought of as functions that take zero or more parameters and do not return any value (i.e., they are of type void). They can be declared and then invoked with appropriate arguments within the instruction sequence.

A procedure declaration consists of:
- a header, containing information about the procedure name and its parameters (all of integer type). The procedure name is a non-empty string of English alphabet letters from 'a' to 'z', and parameter names are subject to the same restrictions as variable names, i.e., they are single-letter names. All parameter names must be unique. Parameters are passed by value. Procedure declarations work similarly to variable declarations:
  - they are located in the same block, at the beginning of the block among variable and procedure declarations,
  - they are visible until the end of their block,
  - they can be overridden,
  - you cannot declare two procedures with the same name in the same block,
- the body of the procedure, consisting of a sequence of declarations followed by a sequence of instructions executed when the procedure is called.

A procedure call is an instruction containing:
- the procedure name,
- arguments, which are expressions in the Macchiato language.

Arguments are evaluated at the time of the procedure call (in the order they are written, i.e., from the first to the last) and are available in the procedure body as variable values corresponding to the procedure parameters. Invoking a procedure results in executing the instructions in its body sequentially, with variable binding happening dynamically. This means that if there is a reference to a variable in the procedure body, determining which variable it refers to (if there is more than one variable with the same name in the program and whether such a variable exists at all) is done during the procedure execution. The currently visible variable in the procedure execution environment is used. Note: dynamic variable binding is easier to implement (which is why it was chosen in Macchiato), but it is practically not used. In languages like C, Java, or Python, static variable binding is used.

## 2. New debugger command
The debugger for Macchiato version 1.1 includes support for a new command called "dump," which allows dumping the program memory to a file. This command has the symbol "m" and requires one parameter, which is the file path. The effect of the command should be to save the program memory dump to the specified file in textual form. The program memory dump consists of:
- visible procedure declarations, i.e., their names along with parameter names (without the body),
- current variable valuations (as in the "d 0" command).

## 3. Convenient program creation in Java
In Macchiato version 1.1, programs can be created in a much more convenient way than in the previous version. A set of classes that form a small SDK for Macchiato provides the ability to create programs and their individual parts in a DSL-like manner, allowing for sequentially adding individual declarations and instructions through method calls (see the Builder design pattern), as well as creating expressions using readable, static functions (see the Factory design pattern). Creating a program with the following meta-syntax:

```
begin block
    var x 101
    var y 1
    proc out(a)
        print a+x
    end proc
    x := x - y
    out(x)
    out(100)  // should print 200 here
    begin block
        var x 10
        out(100) // still 200 statically, 110 dynamically
    end block
end block
```

could proceed as follows:

```
var program = new ProgramBuilder()
    .declareVariable('x', Constant.of(101))
    .declareVariable('y', Constant.of(1))
    .declareProcedure('out', List.of('a'), new BlockBuilder()
         .print(Addition.of(Variable.named('a'), Variable.named('x')))
         .build()
    )  
    .assign('x', Subtraction.of(Variable.named('x'), Variable.named('y')))
    .invoke('out', List.of(Variable.named('x')))
    .invoke('out', List.of(Constant.of(100)))
    .block(new BlockBuilder()
        .declareVariable('x', Constant.of(10))
        .invoke('out', List.of(Constant.of(100)))
        .build() 
    )
    .build();
```

## Tests

The project with the solution must be supplemented with JUnit tests. Each syntactic construction of the Macchiato 1.1 language should be accompanied by one test.

## Submission Format

The task involves implementing Macchiato version 1.1 according to the provided specification. The solution should be in the form of a project named `po_macchiato` created on the faculty's Gitlab. You should start by creating an empty project and placing your solution for the first part of the task in it as the first commit on the master branch. When working on the solution for the second part, you should follow good version control practices regarding commit names, their structure, etc. (for the purposes of this task, it is assumed that there should be visible work in the form of smaller commits, rather than one huge commit with the entire solution for the second part of the task). Furthermore, you should use feature branches when adding individual functionalities.

As a solution, you should submit a set of packages and classes allowing for executing and tracing programs in Macchiato 1.1, along with the sample program provided in point 3, created in this language, in the main function. Therefore, the Git repository may contain only the necessary .java files and .gitignore. However, binary, temporary, or IDE-specific files/folders should not be included there.

The final solution should be committed to the master branch, which must be pushed to the repository (git push) before the deadline specified in Moodle. You should ensure that the Gitlab project has its visibility set to private and that the course instructor is added as a collaborator to the project with Developer access level.

Participants who do not have their solution for the first task may:
- write only the first task now, it will be graded on a scale of 0-10, as if it were the submission (itself) of the second task,
- write the necessary parts of the first task and the entire second task, in which case points will only be awarded for the second task.

Modification history:
- 10th June 2023, v1.2: Added a new, recommended version of the example (required in the code). This version illustrates the difference between dynamic and static variable binding. We will also accept static binding (the text mentions dynamic), please just clearly indicate in the code that you have implemented it.

The old version of the example, although not recommended, is still accepted (without affecting the scoring).

Here is the content (for completeness of the change history) of the old version:

```
begin block
    var x 57
    var y 15
    proc out(a)
        print a
    end proc
    x := x - y
    out(x)
    out(125)
end block
```

and the old sample code

```
var program = new ProgramBuilder()
    .declareVariable('x', Constant.of(57))
    .declareVariable('y', Constant.of(15))
    .declareProcedure(
      'out', List.of('a'),
      new BlockBuilder().print(Variable.named('a')).buildProc()
    )  
    .assign('x', Subtraction.of(Variable.named('x'), Variable.named('y')))
    .invoke('out', List.of(Variable.named('x')))
    .invoke('out', List.of(Constant.of(125)))
    .build();
```
