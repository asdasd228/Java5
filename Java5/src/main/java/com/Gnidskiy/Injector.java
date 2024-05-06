package com.Gnidskiy;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

public class Injector {
    static Properties properties = new Properties();

    /***
     * Initialize properties file
     */
    static {
        try (InputStream inputStream = Injector.class.getResourceAsStream("/AutoInject.properties")) {
            properties.load(inputStream);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     *
     * @param instance - instance of some class that will be injected
     * @return Same instance with injected fields
     * @param <T> Generic to make all classes injectable
     */
    public <T> T inject(T instance) {
        Field[] fields = instance.getClass().getDeclaredFields();

        for (Field f : fields)
            if (f.isAnnotationPresent(AutoInjectable.class)) {
                String interfaceName = properties.getProperty(f.getType().getName());

                try {
                    Object impl = Class.forName(interfaceName).newInstance();
                    f.setAccessible(true);
                    f.set(instance, impl);
                } catch (Exception ignored) {
                }
            }
        return instance;
    }
}
