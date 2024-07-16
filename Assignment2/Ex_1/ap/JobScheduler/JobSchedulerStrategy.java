package ap.JobScheduler;

import java.util.List;
import java.util.stream.Stream;

public abstract class JobSchedulerStrategy<K, V> {
  
  /*  Hot Spot  */
  
  public abstract Stream<AJob<K, V>> emit();
  public abstract void output(Stream<Pair<K, List<V>>> collect_out);
}
