package reflect;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TestGetMethod implements Serializable{
    private static final String  testString= "hello";
    public static void main(String args[]) throws Exception{
        Class<?> clazz = Class.forName("reflect.TestGetMethod");
        Method[] methods = clazz.getMethods();
        for (Method method :methods){
            Class<?> returnType = method.getReturnType();
            Class<?> para[] = method.getParameterTypes();
            int temp = method.getModifiers();
            System.out.print(Modifier.toString(temp));
            System.out.print(returnType.getName());
            System.out.print(method.getName());

            for (Class par:para){
                System.out.println(par.getName());
            }

//            Class<?> exce[] = method.getExceptionTypes();
//            if (exce.length>0);
//            if (exce.length > 0) {
//                System.out.print(") throws ");
//                for (int k = 0; k < exce.length; ++k) {
//                    System.out.print(exce[k].getName() + " ");
//                    if (k < exce.length - 1) {
//                        System.out.print(",");
//                    }
//                }
//            } else {
//                System.out.print(")");
//            }
//            System.out.println();
        }
    }
}
