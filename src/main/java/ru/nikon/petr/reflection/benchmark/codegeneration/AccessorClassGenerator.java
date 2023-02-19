package ru.nikon.petr.reflection.benchmark.codegeneration;

import lombok.SneakyThrows;
import net.openhft.compiler.CompilerUtils;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class AccessorClassGenerator {

    private AccessorClassGenerator() {
    }

    @SneakyThrows
    public static Supplier createConstructorLambda(Class<?> beanClass) {
        var packageName = "%s.generated.%s".formatted(CodeGenerationBuilder.class.getPackage().getName(), beanClass.getPackage().getName());

        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), "init");

        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var javaCode = """
                package %s;
                public class %s implements %s {
                    public Object get() {
                        return new %();
                    }
                }
                """;

        javaCode = javaCode.formatted(packageName, simpleClassName, Supplier.class.getName(), beanClass.getName());
        var accessorClass = CompilerUtils.CACHED_COMPILER.loadFromJava(fullClassName, javaCode);
        return (Supplier) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static Function createGetterLambda(Class<?> beanClass, String getterName) {
        var packageName = "%s.generated.%s".formatted(CodeGenerationBuilder.class.getPackage().getName(), beanClass.getPackage().getName());

        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), getterName);

        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var javaCode = """
                package %s;
                public class %s implements %s {
                    public Object apply(Object bean) {
                        return ((%s) bean).%s(value));
                    }
                }
                """;

        javaCode = javaCode.formatted(packageName, simpleClassName, Function.class.getName(), beanClass.getName(), getterName);
        var accessorClass = CompilerUtils.CACHED_COMPILER.loadFromJava(fullClassName, javaCode);
        return (Function) accessorClass.getConstructor().newInstance();
    }

    @SneakyThrows
    public static BiConsumer createSetterLambda(Class<?> beanClass, String setterName) {
        var packageName = "%s.generated.%s".formatted(CodeGenerationBuilder.class.getPackage().getName(), beanClass.getPackage().getName());

        var simpleClassName = "%s$%s".formatted(beanClass.getSimpleName(), setterName);

        var fullClassName = "%s.%s".formatted(packageName, simpleClassName);

        var javaCode = """
                package %s;
                
                public class %s implements %s {
                    public void accept(Object bean, Object value) {
                        return ((%s) bean).%s(value));
                    }
                }
                """;

        javaCode = javaCode.formatted(packageName, simpleClassName, BiConsumer.class.getName(), beanClass.getName(), setterName);
        var accessorClass = CompilerUtils.CACHED_COMPILER.loadFromJava(fullClassName, javaCode);
        return (BiConsumer) accessorClass.getConstructor().newInstance();
    }
}
