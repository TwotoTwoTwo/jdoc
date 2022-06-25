package org.jdoc;


import org.jdoc.doclet.Jdoclet;


import javax.tools.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class App {

    public static void main(String[] args) throws Exception {
        System.exit(execute(args));
    }

    /**
     * Programmatic interface.
     *
     * @param args the command-line parameters
     * @return The return code.
     */
    public static int execute(String... args) throws InvocationTargetException, IllegalAccessException {
        Locale.setDefault(new Locale("en","US"));


        List<String> options = Arrays.asList("--source-path=/Users/wsjiu/BugCode/jdoc/src/main/java",
                "--class-path=/Users/wsjiu/BugCode/jdoc", "org.jdoc");


        DocumentationTool documentationTool = ToolProvider.getSystemDocumentationTool();
        String path = "/Users/wsjiu/BugCode/jdoc/src/main/java/org/jdoc/doclet/Jdoclet.java";
        StandardJavaFileManager fileManager = documentationTool.getStandardFileManager(null, null, null);
        Iterable<JavaFileObject> fileObjects = (Iterable<JavaFileObject>) fileManager.getJavaFileObjects(path);
        DocumentationTool.DocumentationTask task = documentationTool.getTask(null, fileManager,
                null, Jdoclet.class, options, fileObjects);
        task.call();
        return 0;
    }
}
