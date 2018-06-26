package randomIntClassSnifer;

import java.lang.reflect.Field;
import java.util.Random;

public class IntStower {
    public static void stow (Object object) throws IllegalAccessException {
        Class classOfObject = object.getClass();

        Field[] fields = classOfObject.getDeclaredFields();

        for(Field field: fields){
            RandomInt annotation = field.getAnnotation(RandomInt.class);
            if (annotation != null){
                Random randomize = new Random();
                int min = annotation.min();
                int max = annotation.max();

                field.setAccessible(true);
                field.set(object, randomize.nextInt(max - min) + min );

            }
        }


    }
}
