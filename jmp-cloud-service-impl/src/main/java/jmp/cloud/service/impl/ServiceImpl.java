package jmp.cloud.service.impl;

import jmp.dto.BankCard;
import jmp.service.api.Service;
import jmp.dto.Subscription;
import jmp.dto.User;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Optional;



public class ServiceImpl implements Service {

    private final Map<User, List<BankCard>> userCards = new HashMap<>();
    private final List<Subscription> subscriptions = new ArrayList<>();

    @Override
    public void subscribe(BankCard bankCard) {
        var user = bankCard.getUser();
        if (!userCards.containsKey(bankCard.getUser())) {
            userCards.put(bankCard.getUser(), new ArrayList<>());
        }
        userCards.get(user).add(bankCard);
        subscriptions.add(new Subscription(bankCard.getNumber(), LocalDate.now()));
    }

    @Override
    public Optional<Subscription> getSubscriptionByBankCardNumber(String number) {

        return Optional.ofNullable(subscriptions.stream()
                .filter(subscription -> subscription.getBankcard().equals(number))
                .findFirst()
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found for bank card: " + number)));

    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userCards.keySet());
    }

    @Override
    public List<Subscription> getAllSubscriptionsByCondition(Predicate<Subscription> condition) {

        List<Subscription> subscriptionsByCondition=subscriptions.stream()
                        .filter(condition)
                        .collect(Collectors.collectingAndThen(
                                Collectors.toList(),
                                Collections::unmodifiableList

                        ));
        if(subscriptionsByCondition.isEmpty()){
            throw new SubscriptionNotFoundException("Not found subscription, User doesn't met condition!");
        }
        return  subscriptionsByCondition;
    }


}
