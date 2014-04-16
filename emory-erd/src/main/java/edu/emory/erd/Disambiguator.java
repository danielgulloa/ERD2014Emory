package edu.emory.erd;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.AnnotationSet;

import java.util.List;

/**
 * Given a list of all possible mentions for a document Disambiguator builds non-contradicting AnnotationsSets.
 * If multiple interpretations are possible it returns multiple AnnotationSets.
 */
public interface Disambiguator {
    /**
     * Finds non-contradicting and probable sets of annotations and returns them as a list of annotation sets.
     * @param mentions Mentions found by mention builder.
     * @return List of AnnotationSets representing possible different interpretations of entities in the document.
     */
    public List<AnnotationSet> disambiguate(List<Annotation> mentions);
}
