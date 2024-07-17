import ap.JobScheduler.AJob;
import ap.JobScheduler.JobSchedulerStrategy;
import ap.JobScheduler.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

// AnagramCounterStrategy defines a strategy for counting anagrams in text files.
public class AnagramCounterStrategy extends JobSchedulerStrategy<String, String> {
  @Override
  public Stream<AJob<String, String>> emit() {
    System.out.print("Enter the absolute directory path: ");
    
    Scanner scanner = new Scanner(System.in);
    String directoryPath = scanner.nextLine().trim(); // Read directory path.
    scanner.close();

    // Check if directoryPath is an absolute path
    if (!Paths.get(directoryPath).isAbsolute()) {
      System.err.println("Error: directoryPath must be an absolute path");
      System.exit(1);
    }

    Stream<AJob<String, String>> jobs = Stream.empty();
    
    try {
      // List text files in the directory, create AnagramCounterJob instances.
      jobs = Files.list(Paths.get(directoryPath))
                  .filter(path -> path.toString().endsWith(".txt") && !Files.isDirectory(path))
                  .map(path -> new AnagramCounterJob(path.toString()));
    } catch (IOException e) {
      System.err.println(e);
      System.exit(1);
    }

    return jobs;
  }

  @Override
  public void output(Stream<Pair<String, List<String>>> collect_out) {
    String filePath = "count_anagrams.txt";

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      // Write each pair to the file.
      collect_out.forEach(pair -> {
        try {
          writer.append("\"" + pair.getKey() + "\" - " + pair.getValue().size() + "\n");
        } catch (IOException e) {
          System.err.println(e);
          System.exit(1);
        }
      });

      System.out.println("Anagram counts have been saved to '" + filePath + "'");
    } catch (IOException e) {
      System.err.println(e);
      System.exit(1);
    }
  }
}