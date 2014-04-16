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

    public Annotation(String doc, int begin, int end, EntityInfo info) {
        sourceText = new Text(doc);
        span = new Span(begin, end);
        entityInfo = info;
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

}
