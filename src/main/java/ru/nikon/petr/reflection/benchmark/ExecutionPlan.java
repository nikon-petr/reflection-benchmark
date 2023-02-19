package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.HashMap;
import java.util.Map;

@State(Scope.Benchmark)
public class ExecutionPlan {

    public SampleObjectBuilder builder;

    public Map<String, Object> values;

    {
        values = new HashMap<>();
        values.put("stringValue", "test_string");
        values.put("intValue", 1_000_000);
    }

    @Setup(Level.Trial)
    public void setUp() { }
}
