package sergeyrusakov.testingtask;

import javax.persistence.*;

@Entity
@Table(name="bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private int id;

    @Column(name="holder_name", nullable = false)
    private String holderName;

    @Column(name = "balance")
    private int balance;

    public BankAccount() {

    }

    public int getId() {
        return id;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "id=" + id +
                ", holderName='" + holderName + '\'' +
                ", balance=" + balance +
                '}';
    }

    @Override
    public int hashCode() {
        return id+holderName.hashCode();
    }
}
