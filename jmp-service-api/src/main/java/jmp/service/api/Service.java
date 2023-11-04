package jmp.service.api;

import jmp.dto.BankCard;
import jmp.dto.Subscription;
import jmp.dto.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.stream.IntStream;

public interface Service {

    LocalDate now = LocalDate.now();

    void subscribe(BankCard bankCard);

    Optional<Subscription> getSubscriptionByBankCardNumber(String number);

    List<User> getAllUsers();

    default double getAverageUsersAge() {
        long totalAge = getAllUsers().stream()
                .mapToLong(user -> ChronoUnit.YEARS.between(user.getBirthday(), now))
                .sum();
        return (double) totalAge / getAllUsers().size();
    }

    public static boolean isPayableUser(User user, double averageAge) {
        if (averageAge >= 18.0) {
            System.out.println(user.getName() + " is payable");
            return true;
        }
        System.out.println(user.getName() + " is not payable");
        return false;
    }

    List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition);
}

