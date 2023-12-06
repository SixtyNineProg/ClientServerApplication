package by.klimov;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
public class Client {
  private final ExecutorService executor;
  private final List<Integer> data;
  private final AtomicInteger accumulator = new AtomicInteger(0);

  public Client(int n, Server server) {
    this.data = new ArrayList<>(n);
    for (int i = 1; i <= n; i++) {
      this.data.add(i);
    }
    this.executor = Executors.newFixedThreadPool(10);
    Random rand = new Random();
    while (!data.isEmpty()) {
      int index = rand.nextInt(data.size());
      int value = data.remove(index);
      executor.submit(
          () -> {
            try {
              Thread.sleep(rand.nextInt(400) + 100L);
              Response response = server.processRequest(new Request(value));
              log.info("Response size: {}", response.getSize());
              accumulator.addAndGet(response.getSize());
            } catch (InterruptedException e) {
              Thread.currentThread().interrupt();
            }
          });
    }
    executor.shutdown();
  }

  public int getAccumulator() {
    return accumulator.get();
  }
}
