package sergeyrusakov.testingtask;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;

public class AccountProcessor {
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private AccountsRepository repository;

    //Обрабатывает аккаунт в новом потоке (прибавляет к балансу генерируемое значение)
    public CompletableFuture<BankAccount> processAccount(BankAccount account){
        return CompletableFuture.supplyAsync(()->{
            Random random = new Random();
            int sum = random.nextInt(1000)-500;
            int balanceBefore = account.getBalance();
            if(balanceBefore+sum<0) return account;
            account.setBalance(balanceBefore+sum);
            return account;
        },executorService);
    }

    //Сохранение объектов в базе данных
    public void saveAccount(BankAccount account) {
        repository.save(account);
    }

    //Достаёт аккаунт из CompletableFuture. Работает ради исключения и прерывания операции
    public BankAccount getAccount(CompletableFuture<BankAccount> accountCompletableFuture) throws ExecutionException, InterruptedException {
        return accountCompletableFuture.get();
    }
}
