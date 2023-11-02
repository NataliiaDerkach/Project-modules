package jmp.cloud.service.impl;

import jmp.dto.BankCard;
import jmp.service.api.Service;
import jmp.dto.Subscription;
import jmp.dto.User;

import java.time.LocalDate;
import java.util.*;


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

        return subscriptions.stream().filter(subscription -> subscription.getBankcard().equals(number)).findFirst();
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userCards.keySet());
    }


}
