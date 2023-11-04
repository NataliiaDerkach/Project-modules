package jmp.cloud.bank.impl;

import jmp.bank.api.Bank;
import jmp.dto.*;
import java.util.UUID;


public class BankImpl implements Bank {


    @Override
    public BankCard createBankCard(User user, BankCardType cardType) {

        if (BankCardType.CREDIT == cardType) {
            return new CreditBankCard(UUID.randomUUID().toString(), user);
        } else if (BankCardType.DEBIT == cardType) {
            return new DebitBankCard(UUID.randomUUID().toString(), user);
        } else {
            throw new IllegalArgumentException("Wrong card type" + cardType);
        }
    }
}

