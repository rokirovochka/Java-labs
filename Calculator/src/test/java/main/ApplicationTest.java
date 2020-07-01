package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

class ApplicationTest {

    @org.junit.jupiter.api.Test
    void main() throws FileNotFoundException {

        FileOutputStream f;
        f = new FileOutputStream(outputFileName);
        System.setOut(new PrintStream(f));

        for (int i = 1; i <= testsCount; i++) {
            String source = inputFileNameWOE + i + txtExtension;
            Calculator calculator = new Calculator(source);
            calculator.run();
        }
        Scanner answer, out;
        answer = new Scanner(new File(outputFileName));
        for (int i = 1; i <= testsCount; i++) {
            out = new Scanner(new File(checkTestFileNameWOE + i + txtExtension));
            while (out.hasNextLine()) {
                assertEquals(out.nextLine(), answer.nextLine());
            }
        }
    }
    static final String outputFileName = "out/answer.txt";
    static final String inputFileNameWOE = "test/test";
    static final String checkTestFileNameWOE = "out/out";
    static final String txtExtension = ".txt";
    static final int testsCount = 9;
}