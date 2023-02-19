package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(time = 3)
    @Measurement(time = 3)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void hard_code(HardCodedBuilderExecutionPlan executionPlan, Blackhole blackhole) {
        for (int i = executionPlan.iterations; i > 0; i--) {
            blackhole.consume(executionPlan.builder.build(executionPlan.values));
        }
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(time = 3)
    @Measurement(time = 3)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void lambda_metafactory(LambdaMetafactoryBuilderExecutionPlan executionPlan, Blackhole blackhole) {
        for (int i = executionPlan.iterations; i > 0; i--) {
            blackhole.consume(executionPlan.builder.build(executionPlan.values));
        }
    }

    @Fork(value = 1, warmups = 1)
    @Warmup(time = 3)
    @Measurement(time = 3)
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void code_generation(CodeGenerationExecutionPlan executionPlan, Blackhole blackhole) {
        for (int i = executionPlan.iterations; i > 0; i--) {
            blackhole.consume(executionPlan.builder.build(executionPlan.values));
        }
    }
}
