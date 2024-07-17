package ap.JobScheduler;

import java.util.List;
import java.util.stream.Stream;

// JobSchedulerStrategy defines the strategy interface for job scheduling.
public abstract class JobSchedulerStrategy<K, V> {
  /*  Hot Spot  */
  
  // Emit method.
  public abstract Stream<AJob<K, V>> emit();

  // Output method.
  public abstract void output(Stream<Pair<K, List<V>>> collect_out);
}
