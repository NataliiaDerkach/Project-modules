package jmp.application;


import jmp.bank.api.Bank;
import jmp.cloud.bank.impl.BankImpl;
import jmp.dto.BankCard;
import jmp.dto.BankCardType;
import jmp.dto.User;
import jmp.service.api.Service;
import jmp.cloud.service.impl.ServiceImpl;


import java.time.LocalDate;

public class Application {

    public static void main(String[] args) {

        System.out.println("Demonstrate");
        Bank bank = new BankImpl();
        Service service = new ServiceImpl();
        User user = new User("Billy", "Wong", LocalDate.of(1980, 12, 11));
        BankCard creditCard = bank.createBankCard(user, BankCardType.CREDIT);
        System.out.println("make subscription");
        service.subscribe(creditCard);

        System.out.println("----------------------");
        System.out.println("verify");
        service.getAllUsers().forEach(System.out::println);
        service.getSubscriptionByBankCardNumber(creditCard.getNumber())
                .ifPresentOrElse(System.out::println, ()->System.out.println("not found"));

    }
}
