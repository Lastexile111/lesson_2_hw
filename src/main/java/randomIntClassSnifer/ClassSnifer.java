package randomIntClassSnifer;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
/*
Написать аннотацию RandomInt. У нее два параметра min() и max(). Default значений нет.
Мин - минимальное значение, которое может быть. Max - максимальное. Т.е. значение будет сгенерировано
 в пределах [min, max]. Эта аннотация должна стоять над полями.
Теперь сама задача:
Вам дана на вход метода - строка - имя пакета target.classes.randomIntClassSnifer.ru.levelup.course
Вы должны просканировать пакет в поисках классов и создать объекта каждого найденного класса.
Эти объекты положить в коллекцию и вывести на экран. У тех полей, у которых будет стоять аннотация
@RandomInt  должно проставить рандомное значение (в пределах диапазона).
 */
public class ClassSnifer {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {


        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the directory path\n");
        if(scan.hasNextLine()){
            String line = scan.nextLine();
            char[] chArray = line.toCharArray();
            String path = "";
            for(int i = 0 ; i < chArray.length; i++) {
                if ((chArray[i] == '.') || (chArray[i] == '/')) {
                    chArray[i] = '\\';
                }
                path += chArray[i];
            }

            File dir = new File(path);


            File[] listOfFiles = dir.listFiles();//получаем список файлов в деректории
            for(File classFile:listOfFiles){

                System.out.println(classFile.getPath());
                char[] chArr = classFile.getName().toCharArray();
                String nameOfClass = "";
                for(int i = 0 ; i < chArr.length; i++) {
                    if (chArr[i] != '.'){
                        nameOfClass += chArr[i];
                    }else{
                        break;
                    }
                }
                Class classOfObject = Class.forName("randomIntClassSnifer.ru.levelup.course."+nameOfClass);

                Object newObject = classOfObject.newInstance();// создаем его объект
                IntStower.stow(newObject);
                System.out.println(toString(newObject));
            }

        }else{
            System.out.println("Wrong Path!");
        }



    }


//позаимствован из домашней работы 1 для удобной проверки target.classes.randomIntClassSnifer.ru.levelup.course
    public static String toString(Object object) throws IllegalAccessException {
        Class classOfObject = object.getClass();
        String result = "> " + object.getClass().getName() + ": ";



        Field[] fields = classOfObject.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            result += field.getName() + " = ";
            if (field.getType().isPrimitive() == true) {

//                if ((field.get(object) != null)){
//                    continue;
//                }
                result += field.get(object);

            }else if(field.getType().isArray() == true){

                int length = Array.getLength(field.get(object));

                for (int j=0; j < length; j++) {
                    result += "[ "+ Array.get(field.get(object), j) + " ]";
                }

            }else if(field.getType().getInterfaces().equals(List.class)){
                result += "[ "+ field.get(object)  + " ]";
            }else if(field.getType().getInterfaces().equals(Queue.class)){
                result += "[ "+ field.get(object)  + " ]";
            }else if(field.getType().getInterfaces().equals(Set.class)){
                result += "[ "+ field.get(object)  + " ]";
            }else if(field.getType().getInterfaces().equals(Map.class)){
                Map objMap = (Map)field.get(object);
                for (Object key : objMap.keySet()){

                    result += "[ Key: " + key + ", value: " + objMap.get(key)  + " ]";

                }

            }else if(field.getType().getInterfaces().equals(Integer.class)){
                result += "[ "+ field.get(object)  + " ]";
            }else if(field.getType().getInterfaces().equals(Double.class)){
                result += "[ "+ field.get(object)  + " ]";
            }else{
                result += "[ "+ field.get(object).toString() + " ]";
            }

            result += "; ";
        }


        return result;
    }
}
