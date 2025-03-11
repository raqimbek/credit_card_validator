package dev.andrylat.raqimbek.bankingutils;

import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public enum PaymentSystem {
    VISA("16", List.of(4)),
    MASTERCARD("16", "51-55"),
    DISCOVER("16", List.of(6011, 65)),
    DINERS_CLUB("16", List.of(36,38)),
    JCB("16", List.of(35)),
    AMERICAN_EXPRESS("16", List.of(34,37));
	
	private int cardMinLength;
	private int cardMaxLength;
	private List<Integer> prefixList = new ArrayList<>();

	private PaymentSystem(String cardLengthRange, List<Integer> prefixes) {
        this(cardLengthRange);
		prefixList.addAll(prefixes);
	}
	
	private PaymentSystem(String cardLengthRange) {
		if (cardLengthRange.indexOf("-") == -1) {
			cardMaxLength = Integer.valueOf(cardLengthRange).intValue();
		} else if (StringUtils.countMatches(cardLengthRange, "-") == 1) {
			cardMinLength = Integer.valueOf(
					cardLengthRange.substring(0, cardLengthRange.indexOf("-"))
					).intValue();
			cardMaxLength = Integer.valueOf(
					cardLengthRange.substring(cardLengthRange.indexOf("-")+1)
					).intValue();
		}

		// idk yet whether it should throw an exception if an argument contains more than one hyphen
	}

	private PaymentSystem(String cardLengthRange, String prefixRange) {
		this(cardLengthRange);
		if (prefixRange.indexOf("-") != -1) {
			var rangeEnds = prefixRange.split("-");
			IntStream.range(
					Integer.valueOf(rangeEnds[0]),
					Integer.valueOf(rangeEnds[1])+1)
			.forEach(prefix -> {
				prefixList.add(prefix);
			});
		}
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
	
	public List<List<Integer>> getPrefixesAsLists() {
		return prefixList.stream()
				.map(n -> List.of(n.toString().split(""))
						.stream()
						.map(Integer::valueOf)
						.toList())
				.toList();
	}
}
