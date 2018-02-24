package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 反射
 */
public class ShowMethods {
    private static String usage = "fasdfadsfadsfadsfdas";
    private static Pattern p  = Pattern.compile("\\w+\\.");
    public static void main(String args[]){
        if (args.length<1){
            System.out.println(usage);
            System.exit(0);
        }

        int lines = 0;
        try{
            Class<?> c = Class.forName(args[0]);
            Method[] methods = c.getMethods();
            Constructor[] ctors = c.getConstructors();
            if (args.length == 1){
                for (Method method:methods){
                    System.out.println(p.matcher(method.toString()).replaceAll(""));
                }
                for (Constructor constructor:ctors){
                    System.out.println(p.matcher(constructor.toString()).replaceAll(""));
                }
            }
        }catch (Exception e){
            System.out.println("没有找到该类");
        }
    }
}
