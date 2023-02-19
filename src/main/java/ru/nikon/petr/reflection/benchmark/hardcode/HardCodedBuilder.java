package ru.nikon.petr.reflection.benchmark.hardcode;

import ru.nikon.petr.reflection.benchmark.SampleObjectBuilder;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class HardCodedBuilder extends SampleObjectBuilder {

    public HardCodedBuilder() {
        super();

        setters = new HashMap<>();
        setters.put("stringValue", (sampleObject, value) -> ((SampleObject) sampleObject).setStringValue((String) value));
        setters.put("intValue", (sampleObject, value) -> ((SampleObject) sampleObject).setIntValue((int) value));

        constructor = SampleObject::new;
    }
}
