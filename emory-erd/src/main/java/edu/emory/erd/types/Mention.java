package edu.emory.erd.types;

/**
 * Represents a mention of an entity. Inside it is a pair of entity info and phrase with confidence score.
 * The difference from Annotation is that mention doesn't have to be a part of text.
 */
public class Mention {
    private final EntityInfo entity;
    private final String phrase;
    private final double score;

    /**
     * Creates a mention for a given entity, phrase with the given score.
     * @param phrase Mention phrase.
     * @param entity Mention entity.
     * @param score Confidence score, could be p(phrase|entity) or p(entity|phrase).
     */
    public Mention(String phrase, EntityInfo entity, double score) {
        if (score < 0 || score > 1)
            throw new IllegalArgumentException("Mention confidence score should be between 0 and 1");
        this.entity = entity;
        this.phrase = phrase;
        this.score = score;
    }

    /**
     * Returns mention phrase.
     * @return mention string.
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * Returns entity.
     * @return EntityInfo object for the current mention.
     */
    public EntityInfo getEntity() {
        return entity;
    }

    /**
     * Returns confidence score for the current mention.
     * @return confidence score between 0 and 1.
     */
    public double getScore() {
        return score;
    }
}
