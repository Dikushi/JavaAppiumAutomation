import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void testGetClassString() {
        assertTrue("Метод getClassString не содержит подстроки 'hello' или 'Hello'",
                mainClass.getClassString().contains("hello") || mainClass.getClassString().contains("Hello"));
    }

    @Test
    public void testGetClassNumber() {
        assertTrue("Значение метода getClassNumber меньше 45", mainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetLocalNumber() {
        assertEquals("Значение метода getLocalNumber не равно 14", 14, mainClass.getLocalNumber());
    }
}