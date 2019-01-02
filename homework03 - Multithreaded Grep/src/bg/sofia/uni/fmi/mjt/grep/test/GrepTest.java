package bg.sofia.uni.fmi.mjt.grep.test;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.IO.Writer;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class GrepTest {

    private Regex regex;
    private Reader reader;
    private Writer writer;
    private List<String> commands;


    @Before
    public void initialize() {
        regex = new Regex();
        reader = new Reader();
        writer = new Writer();
        commands = new ArrayList<>();

        File file = new File("resources/Test.txt");

        String path = file.getAbsolutePath().substring(0, file.getAbsolutePath().length() - 4);

        commands.add("grep -w test " + path + " 3 output");
        commands.add("grep test " + path + " 3 output");
        commands.add("grep -w-i test " + path + " 3 output");

    }


    @Test
    public void testValidationOfDifferentInputs() {

        assertTrue(regex.validateInput(commands.get(0)));
        assertTrue(regex.validateInput(commands.get(1)));
        assertTrue(regex.validateInput(commands.get(2)));

        try {
            reader.read(regex);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDifferentCasesForDifferentParameters() {


    }

}