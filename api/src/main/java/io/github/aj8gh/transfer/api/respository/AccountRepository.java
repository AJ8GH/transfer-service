package io.github.aj8gh.transfer.api.respository;

import io.github.aj8gh.transfer.api.domain.Account;
import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, String> {

}
