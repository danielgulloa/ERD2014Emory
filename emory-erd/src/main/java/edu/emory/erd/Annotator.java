package edu.emory.erd;

import edu.emory.erd.types.AnnotationSet;
import java.util.List;

/**
 * Main interface for Entity annotator
 */
public interface Annotator {
    /**
     * Annotates a document with KB entities.
     * */
    public List<AnnotationSet> annotate(String doc);
}
