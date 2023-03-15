# Reflection benchmark

The main goal of this project is to compare performance of accessor methods wrapped in lambda objects instantinated in three different ways. In all cases they implement `Supplier`, `Function` and `BiConsumer` interfaces. 

### Code generation

Simple accessor classes generated and loaded in runtime.

```java
public class SampleObject$setIntValue implements BiConsumer {
    public SampleObject$setIntValue() {
    }

    public void accept(Object var1, Object var2) {
        ((SampleObject)var1).setIntValue((Integer)var2);
    }
}
```

### Hardcode

Accessor calls wrapped in lambda expressions.

```java
(sampleObject, value) -> ((SampleObject) sampleObject).setIntValue((int) value)
```

### Lambda metafactory
Lambda objects created as a delegation to a `MethodHandle` of accessor methods. In this case method access checking is performed when the `MethodHandle` is created unlike with the Reflection API.

```java
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
```

### Results (JDK 18.0.1.1, OpenJDK 64-Bit Server VM)
```
Benchmark                              Mode  Cnt   Score   Error  Units
BenchmarkRunner._1_code_generation     avgt   25  30.091 ± 0.702  ns/op
BenchmarkRunner._2_hard_code           avgt   25  19.197 ± 1.483  ns/op
BenchmarkRunner._3_lambda_metafactory  avgt   25  18.257 ± 0.614  ns/op
```

### TODO
- add Reflection API case

### Useful links
1. [MethodHandle overview](https://habr.com/ru/company/haulmont/blog/431922/)
2. [MethodHandle javadoc](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/invoke/MethodHandle.html)
3. [LambdaMetafactory overview](https://habr.com/ru/company/haulmont/blog/432418/)
4. [LambdaMetafactory javadoc](https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/invoke/LambdaMetafactory.html)
