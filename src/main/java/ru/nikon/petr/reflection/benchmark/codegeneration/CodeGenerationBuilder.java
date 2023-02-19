package ru.nikon.petr.reflection.benchmark.codegeneration;

import ru.nikon.petr.reflection.benchmark.SampleObjectBuilder;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.HashMap;

public class CodeGenerationBuilder extends SampleObjectBuilder {

    public CodeGenerationBuilder() {
        super();

        setters = new HashMap<>();
        getters = new HashMap<>();
        setters.put("stringValue", AccessorClassGenerator.createSetterLambda(SampleObject.class, "setStringValue", String.class));
        getters.put("stringValue", AccessorClassGenerator.createGetterLambda(SampleObject.class, "getStringValue"));
        setters.put("intValue", AccessorClassGenerator.createSetterLambda(SampleObject.class, "setIntValue", int.class));
        getters.put("intValue", AccessorClassGenerator.createGetterLambda(SampleObject.class, "getIntValue"));

        constructor = AccessorClassGenerator.createConstructorLambda(SampleObject.class);
    }
}
