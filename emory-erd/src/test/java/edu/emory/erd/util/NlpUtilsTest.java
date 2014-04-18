package edu.emory.erd.util;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.Text;
import edu.emory.erd.util.NlpUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class NlpUtilsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * Checks sentence detector.
     */
    @Test
    public void testSentenceDetector() {
        String[] sents = NlpUtils.detectSentences("This is sentence one. This is sentence two.");
        assertEquals(2, sents.length);
    }

    @Test
    public void testSentenceDetectorWithAcronym() {
        String[] sents = NlpUtils.detectSentences("This is just a single sentence about U.S.A.");
        assertEquals(1, sents.length);
    }

    @Test
    public void testSentenceDetectorWithLineBreaks() {
        String[] sents = NlpUtils.detectSentences("This is sentence 1... \n\nThis is my sentence two.");
        assertEquals(2, sents.length);

    }

    /**
     * Checks tokenizer.
     */
    @Test
    public void testTokenizer() {
        String[] words = NlpUtils.tokenize("This is just a single sentence about U.S.A.");
        assertEquals(8, words.length);
    }

    @Test
    public void testTokenizerWithExtraSpaces() {
        String[] words = NlpUtils.tokenize("This \n sentence: has 5 words!!");
        // Actually, punctuation will be considered a word here, so 8 in total.
        assertEquals(8, words.length);
    }

    @Test
    public void testEntityDetector() {
        List<Annotation> annotations =
                NlpUtils.detectEntities(new Text("Michael Jackson was born in a Possum City. He was also called Mike."));
        assertEquals(3, annotations.size());
        assertEquals("PERSON", annotations.get(0).getEntityInfo().getId());
        assertEquals("LOCATION", annotations.get(1).getEntityInfo().getId());
        assertEquals("PERSON", annotations.get(2).getEntityInfo().getId());
        assertEquals(30, annotations.get(1).getSpan().getStart());
        assertEquals(41, annotations.get(1).getSpan().getEnd());
    }

}
