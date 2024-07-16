import ap.JobScheduler.AJob;
import ap.JobScheduler.JobSchedulerStrategy;
import ap.JobScheduler.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class AnagramCounterStrategy extends JobSchedulerStrategy<String, String> {
  private String directoryPath;
  private String filePath;

  public AnagramCounterStrategy(String directoryPath, String filePath) {
    this.directoryPath = directoryPath;
    this.filePath = filePath;
  }

  @Override
  public Stream<AJob<String, String>> emit() {
    try {
      return Files.list(Paths.get(directoryPath))
                  .filter(path -> path.toString().endsWith(".txt") && !Files.isDirectory(path))
                  .map(path -> new AnagramCounterJob(path.toString()));
    } catch (IOException e) {
      System.out.println(e);
      return Stream.empty();
    }
  }

  @Override
  public void output(Stream<Pair<String, List<String>>> collect_out) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      collect_out.forEach(pair -> {
        try {
          writer.append("\"" + pair.getKey() + "\" - " + pair.getValue().size() + "\n");
        } catch (IOException e) {
          System.out.println(e);
        }
      });
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}