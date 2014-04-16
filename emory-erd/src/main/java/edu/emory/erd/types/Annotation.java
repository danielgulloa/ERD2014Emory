package edu.emory.erd.types;

import opennlp.tools.util.Span;

/**
 * Represents entity annotation.
 */
final public class Annotation {
    // Text of the whole document that is being annotated
    private final Text sourceText;
    // Annotated span
    private final Span span;
    // Entity the span is annotated with
    private final EntityInfo entityInfo;
    // Confidence score for the entity annotation.
    private final double score;

    public Annotation(Text docText, int begin, int end, EntityInfo info, double score) throws IllegalArgumentException {
        if (score < 0 || score > 1)
            throw new IllegalArgumentException("Annotation confidence score should be from 0 to 1.");

        sourceText = docText;
        span = new Span(begin, end);
        entityInfo = info;
        this.score = score;
    }

    /** Returns the text of the annotated mention
     * It takes substring every time it is called.
     *  */
    public String getMentionText() {
        return sourceText.getSpanText(span);
    }

    /** Get information about the entity */
    public EntityInfo getEntityInfo() {
        return entityInfo;
    }

    /**
     * Returns source document to which the annotated span belongs.
     * @return A text of the document as Text.
     */
    public Text getDocumentText() {
        return sourceText;
    }

    /**
     * Returns a Span object for annotated text.
     * @return An object of class Span which tells us which interval in the document was annotated.
     */
    public Span getSpan() {
        return span;
    }

    /**
     * Returns the confidence score for the current annotation.
     * @return Annotation confidence score (from 0 to 1).
     */
    public double getScore() {
        return this.score;
    }

}
