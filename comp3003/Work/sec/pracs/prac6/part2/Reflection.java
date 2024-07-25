import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class Reflection {
    public static void main(String[] args) {
        String clsName = args[0];
        try {
            Class<?> cls = Class.forName(clsName);
            Constructor<?> constructor = queryConstructors(cls);
            if (constructor != null) {
                Method[] methods = queryMethods(cls);
                if (methods.length > 0) {
                    String constructorArg = UserInterface.userInput("constructor arg: ");
                    printMethods(methods);
                    int methodIndex = UserInterface.userInput(0, methods.length - 1, "method: ");

                    int methodArg = UserInterface.userInput(Integer.MIN_VALUE, Integer.MAX_VALUE, "method arg: ");

                    Object testInstance = (Object) constructor.newInstance(constructorArg);

                    executeMethod(methods[methodIndex], testInstance, methodArg);
                }
            }
        }
        catch (ReflectiveOperationException e) {
            System.out.println(e);
        }
    }

    public static Constructor<?> queryConstructors(Class<?> cls) {
        try {
            return cls.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public static Method[] queryMethods(Class<?> cls) {
        Method[] allMethods = cls.getDeclaredMethods();
        List<Method> filteredMethods = new ArrayList<>();
        for (Method method : cls.getDeclaredMethods()) {
            Class<?>[] parameterTypes = method.getParameterTypes();
            if (!Modifier.isStatic(method.getModifiers()) && 
            parameterTypes.length == 1 &&
            parameterTypes[0].equals(int.class)) {
                filteredMethods.add(method);
            }
        }

        return filteredMethods.toArray(new Method[0]);
    }

    public static void printMethods(Method[] methods) {
        for (int i = 0; i < methods.length; i++) {
            System.out.println(i + ": " + methods[i]);
        }
    }

    public static void executeMethod(Method method, Object instance, int methodArg) {
        try {
            System.out.println(method.invoke(instance, methodArg));
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}