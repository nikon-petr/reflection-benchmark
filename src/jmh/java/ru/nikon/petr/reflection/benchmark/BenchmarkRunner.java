package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 2)
@Fork(5)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BenchmarkRunner {

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void _2_hard_code(HardCodedBuilderExecutionPlan executionPlan, Blackhole blackhole) {
        var sampleObject = executionPlan.builder.build(executionPlan.values);
        blackhole.consume(sampleObject.getStringValue());
        blackhole.consume(sampleObject.getIntValue());
        blackhole.consume(sampleObject);
    }

    @Benchmark
    public void _3_lambda_metafactory(LambdaMetafactoryBuilderExecutionPlan executionPlan, Blackhole blackhole) {
        var sampleObject = executionPlan.builder.build(executionPlan.values);
        blackhole.consume(sampleObject.getStringValue());
        blackhole.consume(sampleObject.getIntValue());
        blackhole.consume(sampleObject);
    }

    @Benchmark
    public void _1_code_generation(CodeGenerationExecutionPlan executionPlan, Blackhole blackhole) {
        var sampleObject = executionPlan.builder.build(executionPlan.values);
        blackhole.consume(sampleObject.getStringValue());
        blackhole.consume(sampleObject.getIntValue());
        blackhole.consume(sampleObject);
    }
}
