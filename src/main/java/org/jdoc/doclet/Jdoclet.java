package org.jdoc.doclet;

import com.sun.source.doctree.DocCommentTree;
import com.sun.source.util.DocTrees;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;

import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.*;

/**
 * @author 四氿
 * @date 20220624
 */
public class Jdoclet implements Doclet {
    Reporter reporter;
    @Override
    public void init(Locale locale, Reporter reporter) {
        reporter.print(Diagnostic.Kind.NOTE, "Doclet using locale: " + locale);
        this.reporter = reporter;
    }

    public void printElement(DocTrees trees, Element e) {
        DocCommentTree docCommentTree = trees.getDocCommentTree(e);
        if (docCommentTree != null) {
            System.out.println("Element (" + e.getKind() + ": "
                    + e + ") has the following comments:");
            System.out.println("Entire body: " + docCommentTree.getFullBody());
            System.out.println("Block tags: " + docCommentTree.getBlockTags());
        }
    }

    @Override
    public boolean run(DocletEnvironment docEnv) {
        reporter.print(Diagnostic.Kind.NOTE, "overviewfile: " + overviewfile);
        // get the DocTrees utility class to access document comments
        DocTrees docTrees = docEnv.getDocTrees();

        // location of an element in the same directory as overview.html
//        try {
//            Element e = ElementFilter.typesIn(docEnv.getSpecifiedElements()).iterator().next();
//            DocCommentTree docCommentTree
//                    = docTrees.getDocCommentTree(e, overviewfile);
//            if (docCommentTree != null) {
//                System.out.println("Overview html: " + docCommentTree.getFullBody());
//            }
//        } catch (IOException missing) {
//            reporter.print(Diagnostic.Kind.ERROR, "No overview.html found.");
//        }
        for (TypeElement c : ElementFilter.typesIn(docEnv.getSpecifiedElements())) {
            for (VariableElement v : ElementFilter.fieldsIn((c.getEnclosedElements()))) {
                printElement(docTrees, v);
                System.out.println(t.getKind() + ":" + t);
                for (Element e : t.getEnclosedElements()) {
                    printElement(docTrees, e);
                }

                for (Element e : t.getTypeParameters()) {
                    printElement(docTrees, e);
                }
            }
        }
        System.out.println("---");
        for (TypeElement t : ElementFilter.typesIn(docEnv.getIncludedElements())) {
            printElement(docTrees, t);
            System.out.println(t.getKind() + ":" + t);
            for (Element e : t.getEnclosedElements()) {
                printElement(docTrees, e);
            }

            for (Element e : t.getTypeParameters()) {
                printElement(docTrees, e);
            }
        }
        return true;
    }

    @Override
    public String getName() {
        return "Example";
    }

    private String overviewfile = "overview-tree.html";

    @Override
    public Set<? extends Option> getSupportedOptions() {
        Option[] options = {
                new Option() {
                    private final List<String> someOption = Arrays.asList(
                            "-overviewfile",
                            "--overview-file",
                            "-o"
                    );

                    @Override
                    public int getArgumentCount() {
                        return 1;
                    }

                    @Override
                    public String getDescription() {
                        return "an option with aliases";
                    }

                    @Override
                    public Option.Kind getKind() {
                        return Option.Kind.STANDARD;
                    }

                    @Override
                    public List<String> getNames() {
                        return someOption;
                    }

                    @Override
                    public String getParameters() {
                        return "file";
                    }

                    @Override
                    public boolean process(String opt, List<String> arguments) {
                        overviewfile = arguments.get(0);
                        return true;
                    }
                }
        };
        return new HashSet<>(Arrays.asList(options));
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // support the latest release
        return SourceVersion.latest();
    }
}
