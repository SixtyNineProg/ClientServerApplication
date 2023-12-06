package by.klimov;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class Client {
  public static final int THREAD_POOL_SIZE = 10;
  private final ExecutorService executor;
  private final List<Integer> data;
  private final AtomicInteger accumulator = new AtomicInteger(0);
  private final Server server;
  private final Random random = new Random();

  public Client(int n, Server server) {
    this.executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
    this.server = server;
    this.data = new ArrayList<>(n);
    for (int i = 1; i <= n; i++) {
      this.data.add(i);
    }
  }

  public void execute(Server server) {
    while (!data.isEmpty()) {
      int index = random.nextInt(data.size());
      int value = data.remove(index);
      executor.submit(
          () -> {
            try {
              TimeUnit.MILLISECONDS.sleep(random.nextInt(400) + 100L);
              Response response = server.processRequest(new Request(value));
              log.info("Response size: {}", response.getSize());
              accumulator.addAndGet(response.getSize());
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }
    executor.shutdown();
    while (!executor.isTerminated()) {}
  }

  public int getAccumulator() {
    return accumulator.get();
  }
}
