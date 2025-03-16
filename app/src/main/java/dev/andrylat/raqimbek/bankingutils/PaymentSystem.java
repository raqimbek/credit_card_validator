package dev.andrylat.raqimbek.bankingutils;

import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public enum PaymentSystem {
    VISA("16", List.of(4)),
    MASTERCARD("16", List.of(51, 52, 53, 54, 55)),
    DISCOVER("16", List.of(6011, 65)),
    DINERS_CLUB("16", List.of(36, 38)),
    JCB("16", List.of(35)),
    AMERICAN_EXPRESS("16", List.of(34, 37));

    private int cardMinLength;
    private int cardMaxLength;
    private List<Integer> prefixList = new ArrayList<>();

    private PaymentSystem(String cardLengthRange, List<Integer> prefixes) {
        if (cardLengthRange.indexOf("-") == -1) {
            cardMaxLength = Integer.valueOf(cardLengthRange).intValue();
        } else if (StringUtils.countMatches(cardLengthRange, "-") == 1) {
            cardMinLength = Integer.valueOf(cardLengthRange.substring(0, cardLengthRange.indexOf("-"))).intValue();
            cardMaxLength = Integer.valueOf(cardLengthRange.substring(cardLengthRange.indexOf("-") + 1)).intValue();
        }

        prefixList.addAll(prefixes);
    }

    public int getCardMaxLength() {
        return cardMaxLength;
    }

    public int getCardMinLength() {
        return cardMinLength;
    }

    public List<Integer> getPrefixes() {
        return prefixList;
    }
}
