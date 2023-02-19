package ru.nikon.petr.reflection.benchmark.codegeneration;

import ru.nikon.petr.reflection.benchmark.SampleObjectBuilder;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.HashMap;

public class CodeGenerationBuilder extends SampleObjectBuilder {

    public CodeGenerationBuilder() {
        super();

        var accessorClassGenerator = new AccessorClassGenerator();

        setters = new HashMap<>();
        setters.put("stringValue", accessorClassGenerator.createSetterLambda(SampleObject.class, "setStringValue", String.class));
        setters.put("intValue", accessorClassGenerator.createSetterLambda(SampleObject.class, "setIntValue", int.class));

        constructor = accessorClassGenerator.createConstructorLambda(SampleObject.class);
    }
}
