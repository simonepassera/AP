from functools import wraps
import time
import threading
import statistics
import json

def bench(_func=None, *, n_threads=1, seq_iter=1, iter=1):
  """Decorator to benchmark a function with multithreading."""
  
  def decorator_bech(func):
    @wraps(func)
    def wrapper_bench(*args, **kwargs):
      def thread_func():
        for _ in range(seq_iter):
          func(*args, **kwargs)

      times = []
      
      for _ in range(iter):
        # Create and start threads
        threads = [threading.Thread(target=thread_func) for _ in range(n_threads)]
        start_time = time.perf_counter()

        for thread in threads:
          thread.start()
        
        for thread in threads:
          thread.join()
        
        end_time = time.perf_counter()
        times.append(end_time - start_time)

      return {
        "fun": func.__name__,
        "args": (args, kwargs),
        "n_threads": n_threads,
        "seq_iter": seq_iter,
        "iter": iter,
        "mean": statistics.mean(times),
        "variance": (statistics.variance(times) if iter > 1 else 0.0)
      }
    
    return wrapper_bench
  
  if _func == None:
    return decorator_bech
  else:
    return decorator_bech(_func)

def test(iter, fun, *args):
  """Run benchmarks for different configurations and save results."""
  
  configurations = [
    (1, 16),
    (2, 8),
    (4, 4),
    (8, 2)
  ]
    
  results = []
    
  # Benchmark for each configuration
  for n_threads, seq_iter in configurations:
    result = bench(fun, n_threads=n_threads, seq_iter=seq_iter, iter=iter)(*args)
    results.append(result)

  # Create filename and save results
  args_str = "_".join(map(str, args))
  file_name = f"{fun.__name__}_{args_str}_{iter}.json"
  with open(file_name, 'w') as f:
    json.dump(results, f, indent=2)
    
  print(f"Results have been written to {file_name}")

def just_wait(n):
  """NOOP for n/10 seconds"""
  time.sleep(n * 0.1)

def grezzo(n):
  """CPU intensive"""
  for i in range(2 ** n):
    pass

if __name__ == "__main__":
  test(10, just_wait, 20)
  test(10, grezzo, 20)

# Results Summary:
# just_wait: I/0-bound; performance scales with the number of threads because each thread waits concurrently.
# grezzo: CPU-bound; performance degrades with more threads due to the GIL, which limits true parallel execution of CPU-bound tasks.