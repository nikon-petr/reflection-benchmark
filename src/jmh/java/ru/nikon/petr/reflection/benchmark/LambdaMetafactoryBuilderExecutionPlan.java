package ru.nikon.petr.reflection.benchmark;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import ru.nikon.petr.reflection.benchmark.lambdametafactory.LambdaBuilder;

public class LambdaMetafactoryBuilderExecutionPlan extends ExecutionPlan {

    @Override
    public void setUp() {
        this.builder = new LambdaBuilder();
    }
}
