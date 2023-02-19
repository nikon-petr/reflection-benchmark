package ru.nikon.petr.reflection.benchmark.codegeneration;

import org.junit.jupiter.api.Test;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.function.BiConsumer;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class AccessorClassGeneratorTest {

    @Test
    void testGetter() {
        BiConsumer setter = AccessorClassGenerator.createSetterLambda(SampleObject.class, "setStringValue", String.class);

        SampleObject obj = new SampleObject();
        String expectedValue = "test";

        assertNull(obj.getStringValue());

        setter.accept(obj, expectedValue);

        assertEquals(expectedValue, obj.getStringValue());
    }
}