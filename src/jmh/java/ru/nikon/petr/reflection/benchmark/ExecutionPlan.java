package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.Map;

@State(Scope.Benchmark)
public class ExecutionPlan {

    public SampleObjectBuilder builder;

    public Map<String, Object> values = Map.of("stringValue", "test_string", "intValue", 1_000_000);

    @Setup(Level.Trial)
    public void setUp() { }
}
