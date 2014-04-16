package edu.emory.erd.types;

import edu.emory.erd.types.Annotation;
import opennlp.tools.util.Span;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Represents a set of non-overlapping and non-contradicting annotations.
 */
public class AnnotationSet implements Iterable<Annotation> {
    private final int setId;
    private Set<Annotation> annotations;
    private double score;  // Confidence score for this annotation set.

    /**
     * Create AnnotationSet with the given id.
     * @param id of the annotation set.
     */
    public AnnotationSet(int id) {
        setId = id;
        annotations = new HashSet<Annotation>();
        score = 1.0;
    }

    /**
     * Adds annotation to annotation list and checks list consistency (no overlapping spans annotated).
     * @param annotation Annotation to add to the set.
     */
    public void addAnnotation(Annotation annotation) throws IllegalArgumentException {
        if (annotations.size() == 0) {
            annotations.add(annotation);
        } else {
            // Try all annotations and check that they are not intersecting
            for (Annotation existingAnnotation : annotations) {
                // We can probably speed this up with segment tree, but I think it's ok for now. Document is unlikely
                // to have too many annotations.
                if (annotation.getSpan().intersects(existingAnnotation.getSpan()))
                    throw new IllegalArgumentException("Annotations in the same AnnotationSet cannot overlap");
                // Compare references, not content. We expect documents to be exact same object.
                if (annotation.getDocumentText() != existingAnnotation.getDocumentText())
                    throw new IllegalArgumentException("Annotations in the same AnnotationSet should belong to the " +
                        "same document");
            }
            annotations.add(annotation);
        }
    }

    /**
     * Returns iterator for annotations in the set.
     * @return an instance of class Iterator.
     */
    @Override
    public Iterator<Annotation> iterator() {
        return annotations.iterator();
    }

    /**
     * Returns the id of the current AnnotationSet.
     * @return an integer id of the annotation set.
     */
    public int getSetId() {
        return setId;
    }

    /**
     * Returns the confidence score for the current annotation set.
     * @return Confidence score (from 0 to 1).
     */
    public double getScore() {
        return score;
    }

    /**
     * Sets the new confidence score for the current AnnotationSet.
     * @param newScore new confidence score from 0 to 1.
     * @throws IllegalArgumentException if new score is less than 0 or greater than 1.
     */
    public void setScore(double newScore) throws IllegalArgumentException {
        if (newScore < 0 || newScore > 1)
            throw new IllegalArgumentException("ArgumentSet confidence score should be between 0 and 1.");
        score = newScore;
    }
}
