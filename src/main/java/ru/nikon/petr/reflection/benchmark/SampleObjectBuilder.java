package ru.nikon.petr.reflection.benchmark;

import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class SampleObjectBuilder {

    protected Map<String, BiConsumer> setters;
    protected Map<String, Function> getters;
    protected Supplier constructor;

    public SampleObject build(Map<String, Object> values) {
        var sampleObject = constructor.get();

        for (var e : values.entrySet()) {
            var fieldName = e.getKey();
            var fieldValue = e.getValue();
            setters.get(fieldName).accept(sampleObject, fieldValue);
        }

        return (SampleObject) sampleObject;
    }
}
