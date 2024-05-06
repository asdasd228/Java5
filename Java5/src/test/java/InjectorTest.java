import com.Gnidskiy.Injector;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InjectorTest {
    @Test
    public void testInject() {
        Assertions.assertDoesNotThrow(() -> {
            SomeBean b = new Injector().inject(new SomeBean());
            b.foo();
        });
    }
}
