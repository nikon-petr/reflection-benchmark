package ru.nikon.petr.reflection.benchmark.lambdametafactory;

import ru.nikon.petr.reflection.benchmark.SampleObjectBuilder;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class LambdaBuilder extends SampleObjectBuilder {

    public LambdaBuilder() {
        super();

        setters = new HashMap<>();
        setters.put("stringValue", LambdaMetafactoryUtils.createSetterLambda(SampleObject.class, "setStringValue", String.class));
        setters.put("intValue", LambdaMetafactoryUtils.createSetterLambda(SampleObject.class, "setIntValue", int.class));

        constructor = LambdaMetafactoryUtils.createConstructorLambda(SampleObject.class);
    }
}
