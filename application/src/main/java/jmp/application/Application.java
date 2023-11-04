package jmp.application;


import jmp.bank.api.Bank;
import jmp.cloud.bank.impl.BankImpl;
import jmp.dto.BankCard;
import jmp.dto.BankCardType;
import jmp.dto.Subscription;
import jmp.dto.User;
import jmp.service.api.Service;
import jmp.cloud.service.impl.ServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;


import static jmp.service.api.Service.isPayableUser;

public class Application {

    public static void main(String[] args) {

        System.out.println("Demonstrate");
        Bank bank = new BankImpl();
        Service service = new ServiceImpl();
        User user1 = new User("Billy", "Wong", LocalDate.of(1980, 12, 11));
        User user2 = new User("Emma", "Black", LocalDate.of(2005, 10, 1));
        User user3 = new User("Stive", "Fox", LocalDate.of(2006, 1, 12));

        User curentUser = user2;

        BankCard creditCard = bank.createBankCard(curentUser, BankCardType.CREDIT);
        System.out.println("make subscription");
        service.subscribe(creditCard);

        System.out.println("----------------------");
        System.out.println("verify");
        service.getAllUsers().forEach(System.out::println);
        service.getSubscriptionByBankCardNumber(creditCard.getNumber())
                .ifPresentOrElse(System.out::println, () -> System.out.println("not found"));

        System.out.println("----------------------");
        System.out.println("Verify average age");
        var averageAge = service.getAverageUsersAge();
        System.out.println("Average age = " + averageAge + " years old");


        Predicate<Subscription> condition = subscription -> {
            return isPayableUser(curentUser, averageAge);
        };

        Optional<List<Subscription>> optionalSubscriptions = Optional.ofNullable(service.getAllSubscriptionsByCondition(condition));
        optionalSubscriptions.ifPresentOrElse(System.out::println, () -> System.out.println("User doesn't met condition"));
        System.out.println("Subscription by condition " );
        service.getAllSubscriptionsByCondition(condition).forEach(System.out::println);















    }
}
