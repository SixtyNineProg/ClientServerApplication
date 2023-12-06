package by.klimov;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ClientTest {

  @Test
  void execute_whenExecute_thenGetValidResponses() {
    // Given
    Server server = new Server();
    Client client = new Client(100, server);
    int expected = 5050;

    // When
    client.execute(server);
    int actual = client.getAccumulator();

    // Then
    assertThat(actual).isEqualTo(expected);
  }
}
