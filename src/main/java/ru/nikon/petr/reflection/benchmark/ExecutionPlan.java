package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.*;

import java.util.Map;

@State(Scope.Benchmark)
public class ExecutionPlan {

    @Param({"100", "1000", "100000"})
    public int iterations;

    public SampleObjectBuilder builder;

    public Map<String, Object> values = Map.of("stringValue", "str", "intValue", 100);

    @Setup(Level.Iteration)
    public void setUp() { }
}
