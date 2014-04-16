package edu.emory.erd.util;

import edu.emory.erd.util.NlpUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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

}
