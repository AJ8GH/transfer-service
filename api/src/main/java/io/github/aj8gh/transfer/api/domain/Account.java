package io.github.aj8gh.transfer.api.domain;

import java.time.Clock;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class Account {

  @Id
  private final String id = UUID.randomUUID().toString();
  @CreatedDate
  private final Instant createdAt = Instant.now(Clock.systemUTC());
  @NonNull
  private Currency currency;
  private long balance;

  public void debit(long amount) {
    if (amount > balance) {
      throw new IllegalArgumentException();
    }
    balance -= amount;
  }

  public void credit(long amount) {
    balance += amount;
  }

  @Generated
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Account account = (Account) o;
    return balance == account.balance && Objects.equals(id, account.id)
        && currency == account.currency && createdAt.equals(account.createdAt);
  }

  @Generated
  @Override
  public int hashCode() {
    return Objects.hash(id, balance, currency, createdAt);
  }
}
