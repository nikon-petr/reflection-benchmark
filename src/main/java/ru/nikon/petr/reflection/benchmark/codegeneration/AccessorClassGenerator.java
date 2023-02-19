package ru.nikon.petr.reflection.benchmark.codegeneration;

import lombok.SneakyThrows;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class AccessorClassGenerator {

    private final static Map<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public AccessorClassGenerator() {
    }

    @SneakyThrows
    public static Supplier createConstructorLambda(Class<?> beanClass) {
        String packageName = beanClass.getPackage().getName();
        String simpleClassName = String.format("%s$%s", beanClass.getSimpleName(), "init");
        String fullClassName = String.format("%s.%s", packageName, simpleClassName);

        Class<?> accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            String javaCode = "package %s;\n" +
                    "\n" +
                    "public class %s implements %s {\n" +
                    "    public Object get() {\n" +
                    "        return new %s();\n" +
                    "    }\n" +
                    "}\n";

            javaCode = String.format(javaCode, packageName, simpleClassName, Supplier.class.getName(), beanClass.getName());
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (Supplier) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static Function createGetterLambda(Class<?> beanClass, String getterName) {
        String packageName = beanClass.getPackage().getName();
        String simpleClassName = String.format("%s$%s", beanClass.getSimpleName(), getterName);
        String fullClassName = String.format("%s.%s", packageName, simpleClassName);

        Class<?> accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            String javaCode = "package %s;\n" +
                    "\n" +
                    "public class %s implements %s {\n" +
                    "    public Object apply(Object bean) {\n" +
                    "        return ((%s) bean).%s();\n" +
                    "    }\n" +
                    "}\n";

            javaCode = String.format(javaCode, packageName, simpleClassName, Function.class.getName(), beanClass.getName(), getterName);
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (Function) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static BiConsumer createSetterLambda(Class<?> beanClass, String setterName, Class<?> setterParamType) {
        String packageName = beanClass.getPackage().getName();
        String simpleClassName = String.format("%s$%s", beanClass.getSimpleName(), setterName);
        String fullClassName = String.format("%s.%s", packageName, simpleClassName);

        Class<?> accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            String setterType = setterParamType.getName();

            String javaCode = "package %s;\n" +
                    "\n" +
                    "public class %s implements %s {\n" +
                    "    public void accept(Object bean, Object value) {\n" +
                    "        ((%s) bean).%s((%s) value);\n" +
                    "    }\n" +
                    "}\n";

            javaCode = String.format(javaCode, packageName, simpleClassName,  BiConsumer.class.getName(),  beanClass.getName(), setterName, setterType);
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (BiConsumer) accessorClass.getConstructor().newInstance();
    }
}
