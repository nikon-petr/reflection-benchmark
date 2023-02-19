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
        var packageName = beanClass.getPackage().getName();
        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), "init");
        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            var javaCode = """
                    package %s;
                                    
                    public class %s implements %s {
                        public Object get() {
                            return new %s();
                        }
                    }
                    """;

            javaCode = javaCode.formatted(packageName, simpleClassName, Supplier.class.getName(), beanClass.getName());
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (Supplier) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static Function createGetterLambda(Class<?> beanClass, String getterName) {
        var packageName = beanClass.getPackage().getName();
        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), getterName);
        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            var javaCode = """
                    package %s;
                                    
                    public class %s implements %s {
                        public Object apply(Object bean) {
                            return ((%s) bean).%s();
                        }
                    }
                    """;

            javaCode = javaCode.formatted(packageName, simpleClassName, Function.class.getName(), beanClass.getName(), getterName);
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (Function) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static BiConsumer createSetterLambda(Class<?> beanClass, String setterName, Class<?> setterParamType) {
        var packageName = beanClass.getPackage().getName();
        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), setterName);
        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var accessorClass = CLASS_CACHE.computeIfAbsent(fullClassName, key -> {
            var setterType = setterParamType.getName();

            var javaCode = """
                package %s;
                
                public class %s implements %s {
                    public void accept(Object bean, Object value) {
                        ((%s) bean).%s((%s) value);
                    }
                }
                """;

            javaCode = javaCode.formatted(packageName, simpleClassName,  BiConsumer.class.getName(),  beanClass.getName(), setterName, setterType);
            return CodeGenerationUtils.load(fullClassName, javaCode);
        });

        return (BiConsumer) accessorClass.getConstructor().newInstance();
    }
}
