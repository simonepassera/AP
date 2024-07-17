package ap.JobScheduler;

import java.util.stream.Stream;

public abstract class AJob<K, V> {
  // Abstract method to execute the job and return key-value pairs.
  public abstract Stream<Pair<K, V>> execute();
}
