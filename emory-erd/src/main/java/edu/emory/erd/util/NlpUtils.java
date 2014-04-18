package edu.emory.erd.util;

import edu.emory.erd.types.Annotation;
import edu.emory.erd.types.EntityInfo;
import edu.emory.erd.types.Sentence;
import edu.emory.erd.types.Text;

import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Word;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class uses OpenNLP to split text into sentences.
 */
// TODO: replace OpenNLP with StanfordNLP for tokenization and sentence detection?
public class NlpUtils {

    private static SentenceDetector sentenceDetector;
    private static Tokenizer tokenizer;
    private static AbstractSequenceClassifier nerTagger;

    static {
        // Create sentence detector from OpenNLP
        try {
            sentenceDetector = new SentenceDetectorME(
                    new SentenceModel(
                            NlpUtils.class.getClassLoader().getResourceAsStream("opennlp-models/en-sent.bin")));
        } catch (IOException e) {
            sentenceDetector = null;
        }
        assert sentenceDetector != null;

        try {
            // Create tokenizer
            tokenizer = new TokenizerME(
                    new TokenizerModel(
                            NlpUtils.class.getClassLoader().getResourceAsStream("opennlp-models/en-token.bin")));
        } catch (IOException e) {
            tokenizer = null;
        }
        assert tokenizer != null;

        try {
            nerTagger = CRFClassifier.getClassifier("edu/stanford/nlp/models/ner/english.conll.4class.distsim.crf.ser.gz");
        } catch (IOException exc) {
            nerTagger = null;
        } catch (ClassNotFoundException exc) {
            nerTagger = null;
        }

        assert nerTagger != null;
    }

    /**
     * Splits text into sentences
     * @param text text to split into sentences
     * */
    public static String[] detectSentences(String text) {
        return sentenceDetector.sentDetect(text);
    }

    /**
     * Tokenizes text into tokens/words.
     * @param text text to tokenize
     * */
    public static String[] tokenize(String text) {
        return tokenizer.tokenize(text);
    }

    /**
     * Detects Names Entities using Stanford Core NLP toolkit.
     * @param doc Document to detect entities in.
     */
    public static List<Annotation> detectEntities(Text doc) {
        List<Annotation> annotations = new ArrayList<Annotation>();

        for (Sentence sentence : doc.getSentences()) {
            List<CoreLabel> entities = nerTagger.classifySentence(Arrays.asList(sentence.getWords()));
            int wordIndex = 0;
            String lastLabel = "O";
            int currentEntityStart = -1;
            int currentEntityEnd = -1;
            for (CoreLabel label : entities) {
                String strLabel = label.get(CoreAnnotations.AnswerAnnotation.class);
                // If current entity is not Other and if previous label is O or we are continuing it.
                if (!strLabel.equals("O") && (lastLabel.equals("O") || lastLabel.equals(strLabel))) {
                    if (currentEntityStart == -1) {
                        currentEntityStart = sentence.getWordSpan(wordIndex).getStart();
                        currentEntityEnd = sentence.getWordSpan(wordIndex).getEnd();
                    } else {
                        currentEntityEnd = sentence.getWordSpan(wordIndex).getEnd();
                    }
                } else {
                    if (!lastLabel.equals("O")) {
                        annotations.add(new Annotation(doc, new Span(currentEntityStart, currentEntityEnd),
                                new EntityInfo(lastLabel, lastLabel), 1.0));
                    }
                    if (strLabel.equals("O")) {
                        currentEntityStart = -1;
                        currentEntityEnd = -1;
                    } else {
                        currentEntityStart = sentence.getWordSpan(wordIndex).getStart();
                        currentEntityEnd = sentence.getWordSpan(wordIndex).getEnd();
                    }
                }
                lastLabel = strLabel;
                ++wordIndex;
            }
        }
        return annotations;
    }
}