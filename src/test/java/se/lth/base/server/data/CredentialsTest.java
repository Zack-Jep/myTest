package se.lth.base.server.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.UUID;
import org.junit.Test;

public class CredentialsTest {
  @Test
  public void saltApplied() {
    Credentials a = new Credentials("a", "123", Role.NONE);
    Credentials b = new Credentials("b", "123", Role.NONE);
    UUID pwd = a.generatePasswordHash(1L);
    UUID m = b.generatePasswordHash(2L);
    assertNotEquals(pwd, m);
    assertEquals(pwd, a.generatePasswordHash(1L));
  }
}
