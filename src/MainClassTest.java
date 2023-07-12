import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {

    private final MainClass mainClass = new MainClass();

    @Test
    public void testGetLocalNumber() {
        Assert.assertEquals("Значение метода getLocalNumber не равно 14",14, mainClass.getLocalNumber());
    }
}