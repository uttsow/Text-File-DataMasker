# Universal Text File Data Masker

This is a universal text file reader that can support text data masking in any number of columns given in the parameter. It only supports strings and numbers for data masking

## Installation

Requires the latest version of JDK
Make sure JDK is set to PATH

##Compile:

```bash
javac TextCleaner.java
```

## Run

```bash
Java TextCleaner <InputFile> <OutputFile> <colm0> <colm1> ...
```

## Assumptions

    -This assumes that the text file will only include text or numbers
    -Columns start at 0!
    -The text files are put in the same location as the TextCleaner.java file and complied from the terminal!
