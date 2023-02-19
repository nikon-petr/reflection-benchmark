package ru.nikon.petr.reflection.benchmark;

import ru.nikon.petr.reflection.benchmark.codegeneration.CodeGenerationBuilder;

public class CodeGenerationExecutionPlan extends ExecutionPlan {

    @Override
    public void setUp() {
        this.builder = new CodeGenerationBuilder();
    }
}
