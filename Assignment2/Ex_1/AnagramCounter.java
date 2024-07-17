import ap.JobScheduler.JobScheduler;

// Main class.
public class AnagramCounter {
  public static void main(String[] args) {
    // Create an instance of AnagramCounterStrategy for the job scheduler.
    AnagramCounterStrategy strategy = new AnagramCounterStrategy();

    // Initialize JobScheduler with the strategy.
    JobScheduler<String, String> js = new JobScheduler<>(strategy);
    
    // Run the job scheduler.
    js.run();
  }
}
