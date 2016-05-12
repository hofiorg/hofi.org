import java.io.File;
import java.io.FileNotFoundException;
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

  private final static String BASEDIR = "..\\web\\article\\" + File.separator;

  public static void main(String[] args) throws IOException, ParseException {
    new ArticleGenerator();
  }

  private ArticleGenerator() throws IOException, ParseException {
    String template = readFile("template/index.html", StandardCharsets.UTF_8);

    List<String> files = new ArrayList<>();
    Files.walk(Paths.get("articles")).forEach(filePath -> {
      try {
        if (Files.isRegularFile(filePath) && filePath.toString().endsWith(".html")) {
          generateArticle(filePath.toString(), template);
          files.add(filePath.toString());
        } else {
          if(Files.isRegularFile(filePath)) {
            copyFile(filePath.toString());
          }
        }
      }
      catch (IOException | ParseException e) {
        e.printStackTrace();
      }
    });
    java.util.Collections.reverse(files);
    generateInhaltsverz(files, template);
  }

  private void generateArticle(String filepath, String template) throws IOException, ParseException  {
    String date = extractDate(filepath);
    String category = extractCategory(filepath);
    String title = extractTitle(filepath);
    String body = readFile(filepath, StandardCharsets.UTF_8);

    // System.out.println("date: " + date);
    // System.out.println("category: " + category);
    // System.out.println("title: " + title);
    // System.out.println("body: " + body);

    template = template.replaceAll("\\$article_category", "&gt; " + replaceDashes(category));
    template = template.replaceAll("\\$article_title", replaceDashes(title));
    template = template.replaceAll("\\$article_date", convertDate(date));
    template = template.replaceAll("\\$article_body", body);
    //template = template.replaceAll("http://www.hofi.org", "");

    template = replaceUmlaute(template);

    String outputFilename = date + File.separator + category;
    createDir(outputFilename);
    writeFile(createFilename(outputFilename, title), template);
  }

  private void generateInhaltsverz(List<String> files, String template) throws IOException, ParseException  {

    template = template.replaceAll("\\$article_category", "");
    template = template.replaceAll("\\$article_title", "Inhalt");
    template = template.replaceAll("\\$article_date", "");

    String body = "";
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

    body += "<ul>";
    for(String key : map.keySet()) {
      body += "<li>" + key;
      body += "<ul>";
      for(String value : map.get(key)) {
        body += "<li><a href=\"" + convertDateBack(key) + "/" + replaceSpaces(value.split("-")[0].trim()) + "/" + replaceSpaces(value.split("-")[1].trim()) + ".html\">" + value + "</a></li>";


      }
      body += "</li>";
      body += "</ul>";
    }
    body += "</ul>";
    template = template.replaceAll("\\$article_body", body);

    template = replaceUmlaute(template);

    writeFile(createFilename("index"), template);
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

  private String extractDate(String filepath) {
    return filepath.split("\\" + File.separator)[1];
  }

  private String extractCategory(String filepath) {
    return filepath.split("\\" + File.separator)[2];
  }

  private String extractTitle(String filepath) {
    return filepath.split("\\" + File.separator)[3].split("\\.")[0];
  }

  private String convertDate(String dateToConvert) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date date = sdf.parse(dateToConvert);
    SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
    return sdf2.format(date);
  }

  private String convertDateBack(String dateToConvert) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    Date date = sdf.parse(dateToConvert);
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
    return sdf2.format(date);
  }

  private String readFile(String path, Charset encoding) throws IOException {
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    return new String(encoded, encoding);
  }

  private String createFilename(String date, String title) {
    return BASEDIR + date + File.separator + title + ".html";
  }

  private String createFilename(String title) {
    return BASEDIR + title + ".html";
  }

  private boolean createDir(String dir) throws IOException {
    return new File(BASEDIR + dir).mkdirs();
  }

  private void copyFile(String filename) throws IOException {

    System.out.println("copyFile: " + filename + " >> " + filename.replace("articles/", BASEDIR));
    Files.copy(Paths.get(filename), Paths.get(filename.replace("articles\\", BASEDIR)), StandardCopyOption.REPLACE_EXISTING);
  }

  private void writeFile(String filename, String content) throws FileNotFoundException {
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
