package sergeyrusakov.testingtask;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
import java.util.concurrent.*;

public class MainService {

    @Autowired
    private AccountProcessor accountProcessor;

    //Вызывает методы по обработке и сохранению списка объектов
    public void processAccounts(Collection<BankAccount> collection){
        List<CompletableFuture<BankAccount>> completableFutureList = new ArrayList<>();
        collection.forEach((x)->completableFutureList.add(accountProcessor.processAccount(x)));
        for(CompletableFuture<BankAccount> future:completableFutureList){
            try {
                BankAccount account = accountProcessor.getAccount(future);
                accountProcessor.saveAccount(account);
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
