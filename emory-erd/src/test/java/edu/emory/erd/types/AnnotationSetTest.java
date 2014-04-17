package edu.emory.erd.types;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.AnnotationSet;

import opennlp.tools.util.Span;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class AnnotationSetTest {

    AnnotationSet annotationSet;

    @Before
    public void setUp() throws Exception {
        annotationSet = new AnnotationSet(0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testScore() {
        annotationSet.setScore(0.75);
        // Check if the score is stored
        assertEquals(0.75, annotationSet.getScore(), 0.0);
        try {
            annotationSet.setScore(2.0);
            fail("setScore should raise exception if value if out of range");
        } catch (IllegalArgumentException exc) {}
    }

    @Test
    public void testOverlapping() {
        Text text = new Text("This is my sample document. It has 2 sentences! Or even three");
        Annotation annotation = new Annotation(text, new Span(5, 7), new EntityInfo("/m/fakeId", "Dummy"), 1.0);
        annotationSet.addAnnotation(annotation);
        assertEquals("is", annotation.getMentionText());
        annotation = new Annotation(text, new Span(0, 6), new EntityInfo("/m/fakeId", "Dummy"), 0.0);
        try {
            annotationSet.addAnnotation(annotation);
            fail("Overlapping mentions shouldn't be allowed in AnnotationSet");
        } catch (IllegalArgumentException exc) {}
    }
}
