package ap.JobScheduler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class JobScheduler<K, V> {
  private JobSchedulerStrategy<K, V> strategy;

  public JobScheduler(JobSchedulerStrategy<K, V> strategy) {
    this.strategy = strategy;
  }

  public void setStrategy(JobSchedulerStrategy<K, V> strategy) {
    this.strategy = strategy;
  }

  public void run() {
    strategy.output(collect(compute(strategy.emit())));
  }
  
  /*  Frozen Spot  */
  
  private Stream<Pair<K, V>> compute(Stream<AJob<K, V>> emit_out) {
    return emit_out.flatMap(AJob::execute);
  }

  private Stream<Pair<K, List<V>>> collect(Stream<Pair<K, V>> compute_out) {
    Map<K, List<V>> valuesByKeys = compute_out.collect(Collectors.groupingBy(Pair::getKey,
                                                                             Collectors.mapping(Pair::getValue, Collectors.toList())));
                                                                                                                                              
    return valuesByKeys.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), entry.getValue()));
  }
}
