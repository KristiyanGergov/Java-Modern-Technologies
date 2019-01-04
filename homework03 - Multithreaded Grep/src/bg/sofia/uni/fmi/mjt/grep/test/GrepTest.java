package bg.sofia.uni.fmi.mjt.grep.test;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.IO.Writer;
import bg.sofia.uni.fmi.mjt.grep.validation.Regex;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class GrepTest {

    private Reader reader;
    private Writer writer;
    private List<String> commands;
    private final String path = "resources";

    @Before
    public void initialize() {
//        reader = new Reader();
        writer = new Writer();
        commands = new ArrayList<>();

        commands.add("grep -i test " + path + " 3 resources\\output.txt");
        commands.add("grep -w test " + path + " 3 resources\\output.txt");
        commands.add("grep test " + path + " 3 resources\\output.txt");
        commands.add("grep -w-i test " + path + "3resources\\output.txt");
        commands.add("grep -f test " + path + " 3 resources\\output.txt");
        commands.add("grep -w " + path + " 3 resources\\output.txt");
    }


    @Test
    public void testValidationOfDifferentInputs() {
        assertTrue(Regex.validateInput(commands.get(0)).matches());
        assertTrue(Regex.validateInput(commands.get(1)).matches());
        assertTrue(Regex.validateInput(commands.get(2)).matches());
        assertFalse(Regex.validateInput(commands.get(3)).matches());
        assertFalse(Regex.validateInput(commands.get(4)).matches());
        assertFalse(Regex.validateInput(commands.get(5)).matches());
    }

    @Test
    public void testDifferentCasesForDifferentParameters() throws IOException {

//        regex.validateInput(commands.get(0));
//        reader.read(regex);
//
//        regex.validateInput(commands.get(1));
//        reader.read(regex);
//
//        regex.validateInput(commands.get(2));
//        reader.read(regex);
//
//        List<String> lines = new ArrayList<>();
//
//        try (BufferedReader br = new BufferedReader(new FileReader(path + "\\output.txt"))) {
//            String line;
//
//            while ((line = br.readLine()) != null) {
//                lines.add(line);
//            }
//
//        }
//
//        final int last = lines.size() - 1;
//        final int beforeLast = lines.size() - 2;
//        final int beforeBeforeLast = lines.size() - 3;
//
//        assertEquals("Test.txt:1:tEst", lines.get(last));
//        assertEquals("Test.txt:1:tEst", lines.get(beforeLast));
//        assertEquals("Test.txt:1:tEst", lines.get(beforeBeforeLast));

    }

}