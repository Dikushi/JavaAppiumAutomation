import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Значение метода getClassNumber меньше 45", mainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Значение метода getLocalNumber не равно 14",14, mainClass.getLocalNumber());
    }
}