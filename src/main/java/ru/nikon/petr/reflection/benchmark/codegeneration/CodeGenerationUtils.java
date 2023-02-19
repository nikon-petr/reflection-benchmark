package ru.nikon.petr.reflection.benchmark.codegeneration;

import lombok.SneakyThrows;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;

public final class CodeGenerationUtils {

    private final static File ROOT = new File("build/generated/sources/accessors/java/main");

    private CodeGenerationUtils() {
    }

    @SneakyThrows
    public static Class<?> load(String fullClassName, String source) {
        File javaFile = saveJavaFile(fullClassName, source);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.getPath());

        ClassLoader classLoader = URLClassLoader.newInstance(new URL[] { ROOT.toURI().toURL() });
        Class<?> clazz = Class.forName(fullClassName, true, classLoader);

        return clazz;
    }

    @SneakyThrows
    private static File saveJavaFile(String fullClassName, String source) {
        File sourceFile = new File(ROOT, fullClassName.replaceAll("\\.", "/") + ".java");

        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        return sourceFile;
    }
}
