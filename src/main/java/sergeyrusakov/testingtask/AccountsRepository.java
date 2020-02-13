package sergeyrusakov.testingtask;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<BankAccount,Integer> {
}
