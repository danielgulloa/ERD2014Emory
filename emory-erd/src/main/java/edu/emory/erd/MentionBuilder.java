package edu.emory.erd;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.Text;

import java.util.List;

/**
 * Given a text, returns all possible annotations, including overlapping and contradicting.
 */
public interface MentionBuilder {
    /**
     * Finds all possible mentions of entities in the document.
     * @param document Document to find mentions in.
     * @return List of entity mentions.
     */
    public List<Annotation> buildMentions(Text document);
}
