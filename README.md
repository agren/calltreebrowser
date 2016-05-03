# Call Tree Browser
Simple tool to browse call trees. Uses a simple DOT-style graph format.

The following input file:
```
f1 -> f2;
f1 -> f3;
f1 -> f4;
f2 -> f1;
f2 -> f3;
```
Generates the following output:

![alt text][mainwindow]

[mainwindow]: https://raw.githubusercontent.com/agren/agren.github.io/master/calltreebrowser/img/calltreebrowser_main.png "Main window"

A `*` after the function name means the function is present earlier in the call chain.

## Usage
```java CallBrowser inputfile```

## Compile
To build:
Run `ant` from src directory. Or compile the java files using `javac`.
