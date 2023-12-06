package by.klimov;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
  public static void main(String[] args) throws InterruptedException {
    Server server = new Server();
    Client client = new Client(100, server);
    TimeUnit.SECONDS.sleep(20);
    log.info("Accumulator: " + client.getAccumulator());
    log.info("Server data: " + server.getData());
  }
}
