package io.github.aj8gh.transfer.api.respository;

import io.github.aj8gh.transfer.api.domain.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, String> {

}
