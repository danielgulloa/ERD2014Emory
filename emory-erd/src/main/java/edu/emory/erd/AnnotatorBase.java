package edu.emory.erd;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.AnnotationSet;
import edu.emory.erd.types.Text;

import java.util.List;

/**
 * Represents a basic entity linking pipeline: mentions builder + disambiguations.
 */
public class AnnotatorBase implements Annotator {
    private MentionBuilder mentionBuilder;
    private Disambiguator disambiguator;

    /**
     * Base annotator pipeline, which consists of applying MentionBuilder and Disambiguator in a sequence.
     * @param doc Document to annotate.
     * @return List of possible annotation sets.
     */
    @Override
    public List<AnnotationSet> annotate(String doc) {
        Text documentText = new Text(doc);
        List<Annotation> mentions = mentionBuilder.buildMentions(documentText);
        return disambiguator.disambiguate(mentions);
    }
}
