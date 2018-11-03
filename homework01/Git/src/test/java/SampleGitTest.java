import bg.sofia.uni.fmi.mjt.git.Repository;
import bg.sofia.uni.fmi.mjt.git.Result;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SampleGitTest {

    private Repository repo;

    @Before
    public void setUp() {
        repo = new Repository();
    }

    @Test
    public void testDeleteFileAfterCheckoutCommit() {
        repo.add("foo.txt");
        repo.commit("Initial");

        repo.add("boo.txt");
        repo.commit("Second");

        repo.add("goo.txt");
        repo.commit("Third");

        repo.remove("boo.txt");
        repo.commit("Forth");

        Result actual = repo.remove("boo.txt");
        assertFail("'boo.txt' did not match any files", actual);

        String hash = repo.getRepository().get("master").get(0).getHash();
        repo.checkoutCommit(hash);

        actual = repo.remove("boo.txt");
        assertFail("'boo.txt' did not match any files", actual);

        hash = repo.getRepository().get("master").get(2).getHash();
        repo.checkoutCommit(hash);


    }

    @Test
    public void testCheckoutCommit() {
        Result result = repo.checkoutCommit("initial");
        assertNotNull(result);
    }

    @Test
    public void testRemoveSingleElement() {
        repo.add("foo.txt", "bar.txt");
        Result result = repo.remove("foo.txt", "baz.txt");

        assertEquals("'baz.txt' did not match any files", result.getMessage());
    }

    @Test
    public void testAdd_SameFile() {
        repo.add("bar.txt", "baz.txt");
        Result actual = repo.add("baz.txt");

        assertEquals("'baz.txt' already exists", actual.getMessage());
    }

    @Test
    public void testAdd_MultipleFiles() {
        Result actual = repo.add("foo.txt", "bar.txt", "baz.txt");
        assertSuccess("added foo.txt, bar.txt, baz.txt to stage", actual);
    }

    @Test
    public void testRemove_DoesNothingWhenAnyFileIsMissing() {
        repo.add("foo.txt", "bar.txt");

        Result actual = repo.remove("foo.txt", "baz.txt");
        assertFail("'baz.txt' did not match any files", actual);

        actual = repo.commit("After removal");
        assertSuccess("2 files changed", actual);
    }

    @Test
    public void testCheckoutBranch_CanSwitchBranches() {
        repo.add("src/Main.java");
        repo.commit("Add Main.java");

        repo.createBranch("dev");
        Result actual = repo.checkoutBranch("dev");
        assertSuccess("switched to branch dev", actual);

        repo.remove("src/Main.java");
        repo.commit("Remove Main.java");
        assertEquals("Remove Main.java", repo.getHead().getMessage());

        actual = repo.checkoutBranch("master");
        assertSuccess("switched to branch master", actual);
        assertEquals("Add Main.java", repo.getHead().getMessage());
    }

    private static void assertFail(String expected, Result actual) {
        assertFalse(actual.isSuccessful());
        assertEquals(expected, actual.getMessage());
    }

    private static void assertSuccess(String expected, Result actual) {
        assertTrue(actual.isSuccessful());
        assertEquals(expected, actual.getMessage());
    }
}