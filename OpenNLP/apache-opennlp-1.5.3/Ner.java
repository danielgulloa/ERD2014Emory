InputStream modelIn = new FileInputStream("en-ner-person.bin");

try {
  TokenNameFinder model = new TokenNameFinderModel(modelIn);
}
catch (IOException e) {
  e.printStackTrace();
}
finally {
  if (modelIn != null) {
    try {
      modelIn.close();
    }
    catch (IOException e) {
    }
  }
}
