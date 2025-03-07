package dev.andrylat.raqimbek.bankingutils;

import java.util.stream.IntStream;
import java.util.stream.Collectors;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;

public enum PaymentSystem {
    VISA(4),
    MASTERCARD("51-55"),
    DISCOVER(6011, 65),
    DINERS_CLUB(36,38),
    JCB(35),
    AMERICAN_EXPRESS(34,37);
	
	private Set<Integer> prefixSet = new HashSet<>();

	private PaymentSystem(int prefix) {
		prefixSet.add(prefix);
	}
	
	private PaymentSystem(String prefixRange) {
		if (prefixRange.indexOf("-") != -1) {
			var rangeEnds = prefixRange.split("-");
			IntStream.range(
					Integer.valueOf(rangeEnds[0]),
					Integer.valueOf(rangeEnds[1])+1)
			.forEach(prefix -> {
				prefixSet.add(prefix);
			});
		}
	}
	
	private PaymentSystem(int prefix1, int prefix2) {
		prefixSet.add(prefix1);
		prefixSet.add(prefix2);
	}
	
	public Set<Integer> getPrefixes() {
		return prefixSet;
	}
	
	public Set<List<Integer>> getPrefixesAsList() {
		return prefixSet.stream()
				.map(n -> List.of(n.toString().split(""))
						.stream()
						.map(Integer::valueOf)
						.toList())
				.collect(Collectors.toSet());
	}
}
