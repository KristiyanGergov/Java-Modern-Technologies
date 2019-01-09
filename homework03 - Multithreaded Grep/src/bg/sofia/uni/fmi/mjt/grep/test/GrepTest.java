package bg.sofia.uni.fmi.mjt.grep.test;

import bg.sofia.uni.fmi.mjt.grep.IO.Reader;
import bg.sofia.uni.fmi.mjt.grep.IO.Writer;
import bg.sofia.uni.fmi.mjt.grep.constants.SearchGroups;
import bg.sofia.uni.fmi.mjt.grep.utility.CommandExecutor;
import bg.sofia.uni.fmi.mjt.grep.validation.InputHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import static junit.framework.TestCase.*;

public class GrepTest {

    private Writer writer;
    private List<String> commands;
    private final String path = "resources";

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setCommands() {
        writer = new Writer(false);
        commands = new ArrayList<>();

        commands.add("grep -i test " + path + " 3 resources\\output.txt");
        commands.add("grep -w test " + path + " 1 resources\\output.txt");
        commands.add("grep test " + path + " 5");
        commands.add("grep -w-i test " + path + "3resources\\output.txt");
        commands.add("grep -f test " + path + " 3 resources\\output.txt");
        commands.add("grep -w " + path + " 3 resources\\output.txt");
    }

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testValidationOfDifferentInputs() {

        final int[] validInputs = new int[]{0, 1, 2};

        for (int invalidInput : validInputs) {
            assertTrue(InputHandler.validateCommand(commands.get(invalidInput)).matches());
        }

        final int[] invalidInputs = new int[]{3, 4, 5};

        for (int invalidInput : invalidInputs) {
            assertFalse(InputHandler.validateCommand(commands.get(invalidInput)).matches());
        }

    }

    @Test
    public void testDifferentCasesForDifferentParameters() {

        assertTrue(InputHandler.wordIsContained("lorem ipsum", "lorem", SearchGroups.WHOLE_WORDS));
        assertFalse(InputHandler.wordIsContained("lorem ipsum", "lore", SearchGroups.WHOLE_WORDS));

        assertTrue(InputHandler.wordIsContained("LoReM ipsum", "lore", SearchGroups.CASE_INSENSITIVE));

        assertTrue(InputHandler.wordIsContained("LorEm ipsUM", "lorem", SearchGroups.WHOLE_WORDS_CASE_INSENSITIVE));
        assertFalse(InputHandler.wordIsContained("lorem ipsum", "lore", SearchGroups.WHOLE_WORDS_CASE_INSENSITIVE));

        assertTrue(InputHandler.wordIsContained("lorem ipsum", "ore", ""));
        assertFalse(InputHandler.wordIsContained("lOrem ipsum", "ore", ""));

    }


    @Test
    public void testWriterPrintsOnConsoleAndInFileCorrect() throws IOException {

        Matcher matcher = InputHandler.validateCommand(commands.get(2));

        assertTrue(matcher.matches());

        writer.write("test", matcher);
        assertEquals("test", outContent.toString());

        matcher = InputHandler.validateCommand(commands.get(0));

        assertTrue(matcher.matches());

        writer.write("test", matcher);

        try (BufferedReader reader = new BufferedReader(new FileReader("resources/output.txt"))) {
            assertEquals("test", reader.readLine());
        }

    }

    @Test
    public void testReaderReturnFalseOrTrueDependingOnFile() {

        Matcher matcher = InputHandler.validateCommand(commands.get(0));

        assertTrue(matcher.matches());

        File file = new File("resources");

        Reader reader = new Reader(file, matcher);

        assertFalse(reader.readFile());

        file = new File("resources\\Test.txt");
        reader = new Reader(file, matcher);

        assertTrue(reader.readFile());
    }


    @Test
    public void testExecutorRunsNoMoreThanThreadsGiven() {

        Matcher matcher = InputHandler.validateCommand(commands.get(0));

        final int threeMaximumThreads = 3;
        assertTrue(matcher.matches());
        assertTrue(assertLess(CommandExecutor.execute(matcher), threeMaximumThreads));

        matcher = InputHandler.validateCommand(commands.get(1));
        assertTrue(matcher.matches());
        assertTrue(assertLess(CommandExecutor.execute(matcher), 1));

        final int fiveMaximumThread = 5;
        matcher = InputHandler.validateCommand(commands.get(2));
        assertTrue(matcher.matches());
        assertTrue(assertLess(CommandExecutor.execute(matcher), fiveMaximumThread));

    }

    private boolean assertLess(int currentlyRunningThreads, int maximumThreads) {
        return currentlyRunningThreads <= maximumThreads;
    }

}