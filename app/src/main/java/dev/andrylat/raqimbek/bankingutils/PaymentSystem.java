package dev.andrylat.raqimbek.bankingutils;

import java.util.stream.IntStream;
import java.util.List;
import java.util.ArrayList;

public enum PaymentSystem {
    VISA(4),
    MASTERCARD("51-55"),
    DISCOVER(6011, 65),
    DINERS_CLUB(36,38),
    JCB(35),
    AMERICAN_EXPRESS(34,37);
	
	private List<Integer> prefixList = new ArrayList<>();

	private PaymentSystem(int prefix) {
		prefixList.add(prefix);
	}
	
	private PaymentSystem(String prefixRange) {
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
	
	private PaymentSystem(int prefix1, int prefix2) {
		prefixList.add(prefix1);
		prefixList.add(prefix2);
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
