package ru.nikon.petr.reflection.benchmark.codegeneration;

import org.junit.jupiter.api.Test;
import ru.nikon.petr.reflection.benchmark.pojo.SampleObject;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class AccessorClassGeneratorTest {

    @Test
    void testGetter() {
        var setter = AccessorClassGenerator.createSetterLambda(SampleObject.class, "setStringValue", String.class);

        var obj = new SampleObject();
        var expectedValue = "test";

        assertNull(obj.getStringValue());

        setter.accept(obj, expectedValue);

        assertEquals(expectedValue, obj.getStringValue());
    }
}