package ru.nikon.petr.reflection.benchmark.lambdametafactory;

import lombok.SneakyThrows;

import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public final class LambdaMetafactoryUtils {

    public final static Map<Class<?>, Class<?>> BOXED_TYPES = new HashMap<>();

    static {
        BOXED_TYPES.put(boolean.class, Boolean.class);
        BOXED_TYPES.put(byte.class, Byte.class);
        BOXED_TYPES.put(short.class, Short.class);
        BOXED_TYPES.put(char.class, Character.class);
        BOXED_TYPES.put(int.class, Integer.class);
        BOXED_TYPES.put(long.class, Long.class);
        BOXED_TYPES.put(float.class, Float.class);
        BOXED_TYPES.put(double.class, Double.class);
    }

    private LambdaMetafactoryUtils() {
    }

    @SneakyThrows
    public static Supplier createConstructorLambda(Class<?> beanClass) {
        var lookup = MethodHandles.lookup();
        var constructor = lookup.findConstructor(beanClass, MethodType.methodType(void.class));
        var site = LambdaMetafactory.metafactory(lookup,
                "get",
                MethodType.methodType(Supplier.class),
                MethodType.methodType(Object.class),
                constructor,
                constructor.type());
        return (Supplier) site.getTarget().invokeExact();
    }

    @SneakyThrows
    public static Function createGetterLambda(Class<?> beanClass, String getterName, Class<?> getterReturnType) {
        var lookup = MethodHandles.lookup();
        var getter = lookup.findVirtual(beanClass, getterName, MethodType.methodType(getterReturnType));
        var getterType = getter.type();

        if (getterReturnType.isPrimitive()) {
            getterType = getterType.changeReturnType(BOXED_TYPES.get(getterReturnType));
        }

        var site = LambdaMetafactory.metafactory(lookup,
                "apply",
                MethodType.methodType(Function.class),
                getterType.erase(),
                getter,
                getterType);
        return (Function) site.getTarget().invokeExact();
    }

    @SneakyThrows
    public static BiConsumer createSetterLambda(Class<?> beanClass, String setterName, Class<?> setterParamType) {
        var lookup = MethodHandles.lookup();
        var setter = lookup.findVirtual(beanClass, setterName, MethodType.methodType(void.class, setterParamType));
        var setterType = setter.type();

        if (setterParamType.isPrimitive()) {
            setterType = setterType.changeParameterType(1, BOXED_TYPES.get(setterParamType));
        }

        var site = LambdaMetafactory.metafactory(lookup,
                "accept",
                MethodType.methodType(BiConsumer.class),
                setterType.erase(),
                setter,
                setterType);
        return (BiConsumer) site.getTarget().invokeExact();
    }
}
