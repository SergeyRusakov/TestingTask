package sergeyrusakov.testingtask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;

public class TaskManager {
    @Autowired
    private AccountsRepository repository;
    private DirectChannel channel;

    public void setChannel(DirectChannel channel) {
        this.channel = channel;
    }

    //Генерирует список объектов и отправляет через GenericMessage в поток
    @Scheduled(fixedDelay = 5000)
    public void getNextObject(){
        ArrayList<BankAccount> accountArrayList = (ArrayList<BankAccount>) repository.findAll();
        LinkedHashSet<BankAccount> processingAccounts = new LinkedHashSet<>();
        int accountsTotal = accountArrayList.size();
        Random random = new Random();
        for(int i=0;i<random.nextInt(accountsTotal);i++){
            processingAccounts.add(accountArrayList.get(random.nextInt(accountsTotal)));
        }
        if(processingAccounts.size()!=0) {
            channel.send(new GenericMessage<>(processingAccounts));
        }
    }

}
