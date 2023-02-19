package ru.nikon.petr.reflection.benchmark.codegeneration;

import ru.nikon.petr.reflection.benchmark.SampleObjectBuilder;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.HashMap;

public class CodeGenerationBuilder extends SampleObjectBuilder {

    public CodeGenerationBuilder() {
        super();

        setters = new HashMap<>();
        setters.put("stringValue", (sampleObject, value) -> AccessorClassGenerator.createSetterLambda(SampleObject.class, "setStringValue"));
        setters.put("intValue", (sampleObject, value) -> AccessorClassGenerator.createSetterLambda(SampleObject.class, "setIntValue"));

        constructor = AccessorClassGenerator.createConstructorLambda(SampleObject.class);
    }
}
