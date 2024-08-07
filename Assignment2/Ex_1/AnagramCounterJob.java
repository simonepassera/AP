import ap.JobScheduler.AJob;
import ap.JobScheduler.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// AnagramCounterJob is a specific job implementation for counting anagrams in a file.
public class AnagramCounterJob extends AJob<String, String> {
  private String filePath;

  // Constructor to initialize with the file path.
  public AnagramCounterJob(String filePath) {
    this.filePath = filePath;
  }

  @Override
  public Stream<Pair<String, String>> execute() {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
      // Read lines from the file, split words, filter and map them to pairs.
      List<Pair<String, String>> pairs = reader.lines()
                                               .flatMap(line -> Stream.of(line.split(" ")))
                                               .filter(word -> word.length() >= 4 && word.matches("[a-zA-Z]+"))
                                               .map(word -> new Pair<>(toCiao(word), word.toLowerCase()))
                                               .collect(Collectors.toList());

      return pairs.stream();
    } catch (IOException e) {
      System.err.println(e);
      return Stream.empty();
    }
  }

  private String toCiao(String word) {
    return Stream.of(word.toLowerCase().split("")).sorted().collect(Collectors.joining());
  }
}