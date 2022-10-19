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
import org.springframework.data.annotation.CreatedDate;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

  @Id
  private final String id = UUID.randomUUID().toString();
  @CreatedDate
  private final Instant createdAt = Instant.now(Clock.systemUTC());
  @NonNull
  private String sourceAccountId;
  @NonNull
  private String targetAccountId;
  @NonNull
  private Currency currency;
  @NonNull
  private Long amount;

  @Generated
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Objects.equals(id, that.id) && sourceAccountId.equals(that.sourceAccountId)
        && targetAccountId.equals(that.targetAccountId) && currency == that.currency
        && Objects.equals(createdAt, that.createdAt);
  }

  @Generated
  @Override
  public int hashCode() {
    return Objects.hash(id, sourceAccountId, targetAccountId, currency, createdAt);
  }
}
