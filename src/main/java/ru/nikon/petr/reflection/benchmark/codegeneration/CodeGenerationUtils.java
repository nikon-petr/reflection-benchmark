package ru.nikon.petr.reflection.benchmark.codegeneration;

import lombok.SneakyThrows;

import javax.tools.ToolProvider;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

public final class CodeGenerationUtils {

    private final static File ROOT = new File("build/generated/sources/accessors/java/main");

    private CodeGenerationUtils() {
    }

    @SneakyThrows
    public static Class<?> load(String fullClassName, String source) {
        var javaFile = saveJavaFile(fullClassName, source);

        var compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFile.getPath());

        var classLoader = URLClassLoader.newInstance(new URL[] { ROOT.toURI().toURL() });
        var clazz = Class.forName(fullClassName, true, classLoader);

        return clazz;
    }

    @SneakyThrows
    private static File saveJavaFile(String fullClassName, String source) {
        var sourceFile = new File(ROOT, fullClassName.replaceAll("\\.", "/") + ".java");

        sourceFile.getParentFile().mkdirs();
        Files.writeString(sourceFile.toPath(), source);

        return sourceFile;
    }
}
