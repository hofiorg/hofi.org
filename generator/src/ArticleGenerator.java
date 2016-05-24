import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ArticleGenerator {

  private final static String BASEDIR = "web" + File.separator + "article" + File.separator;
  private final static String GEN_DIR = "generator" + File.separator;

  public static void main(String[] args) throws IOException, ParseException {
    new ArticleGenerator();
  }

  private ArticleGenerator() throws IOException, ParseException {
    String template = readFile(GEN_DIR + "template/index.html", StandardCharsets.UTF_8);

    List<String> files = new ArrayList<>();
    Files.walk(Paths.get(GEN_DIR + "articles")).forEach(filePath -> {
      try {
        String fp = filePath.toString().replaceAll("generator\\" + File.separator, "");
        if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".html")) {
          generateArticle(fp, template);
          files.add(fp);
        } else {
          if(Files.isRegularFile(filePath)) {
            copyFile(fp);
          }
        }
      }
      catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    });
    java.util.Collections.reverse(files);
    generateInhaltsverz(files, template);
    generateKategorie(files, template);
  }

  private void generateArticle(String filepath, String template) throws IOException, ParseException  {
    String date = extractDate(filepath);
    String category = extractCategory(filepath);
    String title = extractTitle(filepath);
    String body = readFile(GEN_DIR + filepath, StandardCharsets.UTF_8);

    template = modifyTemplate(template, replaceDashes(category), " &gt; ", replaceDashes(title), convertDate(date), body);

    template = replaceUmlaute(template);

    String outputFilename = date + File.separator + category;
    writeFile(createFilename(outputFilename, title), template);
  }

  private Map<String, List<String>> createInhaltsverzMap(List<String> files) throws ParseException{
    Map<String, List<String>> map = new LinkedHashMap<>();

    for (String file: files) {
      String datum = convertDate(extractDate(file));
      String category = replaceDashes(extractCategory(file));
      String title = replaceDashes(extractTitle(file));

      if(map.containsKey(datum)) {
        map.get(datum).add(category + " - " + title);
      }
      else {
        List<String> list = new ArrayList<>();
        list.add(category + " - " + title);
        map.put(datum, list);
      }
    }
    return map;
  }

  private List<String> createKategorieList(List<String> files) {
    Set<String> set = new HashSet<>();
    for (String file: files) {
      String category = replaceDashes(extractCategory(file));
      set.add(category);
    }
    return asSortedList(set);
  }

  private void generateInhaltsverz(List<String> files, String template) throws IOException, ParseException {
    String body = "";

    Map<String, List<String>> map = createInhaltsverzMap(files);
    body += "<ul>";
    for(String key : map.keySet()) {
      body += "<li>" + key;
      body += "<ul>";
      for(String value : map.get(key)) {

        String[] split = value.split("-");

        String value1 = replaceSpaces(split[0].trim());
        String value2 = replaceSpaces(value.substring(value.indexOf("-")+1).trim());
        body += "<li><a href=\"" + convertDateBack(key) + "/" + value1 + "/" + value2 + ".html\">" + value + "</a></li>";
      }
      body += "</li>";
      body += "</ul>";
    }
    body += "</ul>";

    body += "<br/>";
    body += "<h4>Kategorien</h4>";
    body += "<ul>";
    for(String category : createKategorieList(files)) {
      body += "<li><a href=\"./category/" + category + "/index.html\">" + category + "</a></li>";
    }
    body += "</ul>";

    template = modifyTemplate(template, "", "", "Inhalt", "", body);
    template = replaceUmlaute(template);

    writeFile(createFilename("index"), template);
  }

  private static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
    List<T> list = new ArrayList<>(c);
    Collections.sort(list);
    return list;
  }

  private String modifyTemplate(String template, String category, String breadcrumb_sep1, String title, String date, String body) {
    String result = template;
    result = result.replaceAll("\\$article_category", category);
    result = result.replaceAll("\\$article_breadcrumb_sep1", breadcrumb_sep1);
    result = result.replaceAll("\\$article_title", title);
    result = result.replaceAll("\\$article_date", date);
    result = result.replaceAll("\\$article_body", body);
    result = result.replaceAll("http://www\\.hofi\\.org", "");
    return result;
  }

  private Map<String, List<String>> createKategorieMap(List<String> files) throws ParseException{
    Map<String, List<String>> map = new LinkedHashMap<>();

    for (String file: files) {
      String datum = convertDate(extractDate(file));
      String category = replaceDashes(extractCategory(file));
      String title = replaceDashes(extractTitle(file));

      if(map.containsKey(category)) {
        map.get(category).add(datum + "-" + title);
      }
      else {
        List<String> list = new ArrayList<>();
        list.add(datum + "-" + title);
        map.put(category, list);
      }
    }
    return map;
  }

  private void generateKategorie(List<String> files, String template) throws IOException, ParseException {
    Map<String, List<String>> map = createKategorieMap(files);

    for(String category : map.keySet()) {

      List<String> list = map.get(category);
      java.util.Collections.sort(list);

      String body = "";
      body += "<ul>";

      for(String value : list) {
        String[] split = value.split("-");
        String datum = replaceSpaces(split[0].trim());
        String title = replaceSpaces(value.substring(value.indexOf("-")+1).trim());
        body += "<li><a href=\"../../" + convertDateBack(datum) + "/" + category + "/" + replaceSpaces(title) + ".html\">" + replaceDashes(title) + "</a></li>";
      }
      body += "</ul>";

      String myTemplate = modifyTemplate(template, "", "", category, "", body);

      writeFile(createFilename("category", category, "index"), myTemplate);
    }
  }

  private String replaceUmlaute(String template) {
    template = template.replaceAll("\u00fc", "&uuml;");
    template = template.replaceAll("\u00dc", "&Uuml;");
    template = template.replaceAll("\u00f6", "&ouml;");
    template = template.replaceAll("\u00d6", "&Ouml;");
    template = template.replaceAll("\u00e4", "&auml;");
    template = template.replaceAll("\u00c4", "&Auml;");
    return template.replaceAll("\u00df", "&szlig;");
  }

  private String replaceDashes(String value) {
    return value.replaceAll("_", " ");
  }

  private String replaceSpaces(String value) {
    return value.replaceAll(" ", "_");
  }

  private static final String ESCAPE_CHAR = "\\";

  private String splitBySeparator(String value, int i) {
    return value.split(ESCAPE_CHAR + File.separator)[i];
  }

  private String extractDate(String filepath) {
    return splitBySeparator(filepath, 1);
  }

  private String extractCategory(String filepath) {
    return splitBySeparator(filepath, 2);
  }

  private String extractTitle(String filepath) {
    return splitBySeparator(filepath, 3).split("\\.")[0];
  }

  private final static String DATE_US     = "yyyy-MM-dd";
  private final static String DATE_GERMAN = "dd.MM.yyyy";

  private String convertDate(String dateToConvert) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_US);
    Date date = sdf.parse(dateToConvert);
    SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_GERMAN);
    return sdf2.format(date);
  }

  private String convertDateBack(String dateToConvert) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat(DATE_GERMAN);
    Date date = sdf.parse(dateToConvert);
    SimpleDateFormat sdf2 = new SimpleDateFormat(DATE_US);
    return sdf2.format(date);
  }

  private String readFile(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  private String createFilename(String... directory) throws IOException {
    String result = "";
    boolean first = true;

    for (String value : directory) {
      result += (first ? "" : File.separator) + value;
      first = false;
    }

    return BASEDIR + result + ".html";
  }

  private boolean createDir(String dir) throws IOException {
    // System.out.println("Verz. werden erstellt: " + dir);
    return new File(dir).mkdirs();
  }

  private void copyFile(String filename) throws IOException {
    String destFilename = filename.replace("articles" + File.separator, BASEDIR);
    System.out.println("Datei wurde kopiert: " + filename + " >> " + destFilename);
    createDir(Paths.get(destFilename).getParent().toString());

    Files.copy(Paths.get(GEN_DIR + filename), Paths.get(destFilename), StandardCopyOption.REPLACE_EXISTING);
  }

  private void writeFile(String filename, String content) throws IOException {

    String dir = filename.substring(0, filename.lastIndexOf(File.separator));
    createDir(dir);

    System.out.println("Datei wird erstellt: " + filename);
    PrintWriter out = null;
    try {
      out = new PrintWriter(filename);
      out.println(content);
    }
    finally {
      if(out != null)
        out.close();
    }
  }
}
