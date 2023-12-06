package by.klimov;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;
import lombok.Getter;

@Getter
public class Server {
  private final List<Integer> data = new CopyOnWriteArrayList<>();
  private final Random rand = new Random();

  public Response processRequest(Request request) throws InterruptedException {
    TimeUnit.MILLISECONDS.sleep(rand.nextInt(900) + 100L);
    data.add(request.getValue());
    return new Response(data.size());
  }
}
