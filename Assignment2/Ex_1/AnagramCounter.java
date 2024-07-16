import ap.JobScheduler.JobScheduler;

public class AnagramCounter {
  public static void main(String[] args) {
    AnagramCounterStrategy strategy = new AnagramCounterStrategy("./Books", "count_anagrams.txt");
    
    JobScheduler<String, String> js = new JobScheduler<>(strategy);
    js.run();
  }
}
