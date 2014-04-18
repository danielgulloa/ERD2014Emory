import java.io.*;
import java.lang.*;
public class Tutorial{

        public static void findName() throws IOException {
                InputStream is = new FileInputStream("en-ner-person.bin");

                TokenNameFinderModel model = new TokenNameFinderModel(is);
                is.close();

                NameFinderME nameFinder = new NameFinderME(model);

                String []sentence = new String[]{
                            "Mike",
                            "Smith",
                            "is",
                            "a",
                            "good",
                            "person"
                            };

                        Span nameSpans[] = nameFinder.find(sentence);

                        for(Span s: nameSpans)
                                System.out.println(s.toString());
                }


        public static void main(String[] args){

                findName();

        }

}

