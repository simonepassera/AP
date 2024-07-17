package ap.JobScheduler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JobScheduler<K, V> {
  private JobSchedulerStrategy<K, V> strategy;

  // Constructor to initialize with a strategy.
  public JobScheduler(JobSchedulerStrategy<K, V> strategy) {
    this.strategy = strategy;
  }

  // Setter method to change the strategy at runtime.
  public void setStrategy(JobSchedulerStrategy<K, V> strategy) {
    this.strategy = strategy;
  }

  // Method to execute the job scheduling.
  public void run() {
    strategy.output(collect(compute(strategy.emit())));
  }
  
  /*  Frozen Spot  */
  
  // Compute method.
  private Stream<Pair<K, V>> compute(Stream<AJob<K, V>> emit_out) {
    return emit_out.flatMap(AJob::execute);
  }

  // Collect method.
  private Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> compute_out) {
    Map<K, List<V>> valuesByKeys = compute_out.collect(Collectors.groupingBy(Pair::getKey,
                                                                             Collectors.mapping(Pair::getValue, Collectors.toList())));
                                                                                                                                              
    return valuesByKeys.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
  }
}
