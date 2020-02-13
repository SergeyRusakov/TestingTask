package sergeyrusakov.testingtask;

import org.springframework.messaging.support.GenericMessage;

import java.util.Collection;

public class MainServiceActivator {
    private MainService mainService;

    public MainServiceActivator(MainService mainService) {
        this.mainService=mainService;
    }
    //Запускает обработку списка объектов при получении сообщения
    public void startService(GenericMessage<Collection<BankAccount>> message){
            mainService.processAccounts(message.getPayload());
    }
}
