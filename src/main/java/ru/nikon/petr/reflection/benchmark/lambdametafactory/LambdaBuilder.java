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
        getters = new HashMap<>();
        setters.put("stringValue", LambdaMetafactoryUtils.createSetterLambda(SampleObject.class, "setStringValue", String.class));
        getters.put("stringValue", LambdaMetafactoryUtils.createGetterLambda(SampleObject.class, "getStringValue", String.class));
        setters.put("intValue", LambdaMetafactoryUtils.createSetterLambda(SampleObject.class, "setIntValue", int.class));
        getters.put("intValue", LambdaMetafactoryUtils.createGetterLambda(SampleObject.class, "getIntValue", int.class));

        constructor = LambdaMetafactoryUtils.createConstructorLambda(SampleObject.class);
    }
}
