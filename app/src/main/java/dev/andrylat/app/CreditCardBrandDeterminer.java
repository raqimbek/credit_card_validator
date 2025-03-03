package dev.andrylat.app;

public class CreditCardBrandDeterminer {
    private String brand = "";
    private CreditCardValidator validator;

    public CreditCardBrandDeterminer(String input) {
        validator = new CreditCardValidator();
        
    }

    public void determineCreditCardBrandByNumber(String input) {
        var cardNumber = getCardNumber(input);
        determineBrandByNumber(cardNumber);
    }

    private void determineBrandByNumber(List<Integer> cardNumber) {
        /*
           for later improvements:

           I could create an enum with all card brands,
           and each card brand would have a method to validate
           if the given number equals to the brand's possible number
           the return type of the method would be boolean

        */

        switch (cardNumber.get(0)) {
            case 4:
                brand = "VISA";
                break;
            case 5:
                brand = IntStream.range(0,6)
                            .anyMatch(n -> n == cardNumber.get(1))
                            ? "MASTERCARD" : "";
                break;
        }
    }

    private List<Integer> getCardNumber(String s) {
        return new ArrayList<>(Arrays.stream(s.split(""))
                         .filter(c -> !c.equals(" "))
                         .filter(this::isDigit)
                         .map(Integer::valueOf)
                         .toList());
    }

    private boolean isDigit(String s) {
        try {
            Integer.parseInt(s); 
        } catch (NumberFormatException e) {
            var msg = "-> Number should contain only digits\n";

            if (!errors.toString().contains(msg)) {
                errors.append(msg);
            }

            return false;
        }

        return true;
    }
}
