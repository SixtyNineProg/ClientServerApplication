package by.klimov;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ClientTest {

  @Test
  void execute_whenExecute_thenGetValidResponses() {
    // Given
    int awaitTermination = 20;
    Server server = new Server();
    Client client = new Client(100, server);
    int expected = 5050;

    // When
    boolean end = client.execute(server, awaitTermination);
    int actual = client.getAccumulator();

    // Then
    assertThat(actual).isEqualTo(expected);
    assertThat(end).isTrue();
  }

  @Test
  void execute_whenExecuteMoreThanAwaitTermination_thenExecuteTimeOut() {
    // given
    int awaitTermination = 5;
    Server server = new Server();
    Client client = new Client(1000, server);

    // when
    boolean end = client.execute(server, awaitTermination);

    // then
    assertThat(end).isFalse();
  }
}
