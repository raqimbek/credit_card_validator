package dev.andrylat.raqimbek.bankingutils;

import java.util.List;
import java.util.Optional;

public class CardValidationInfo {
    boolean isValid;
    Optional<PaymentSystem> paymentSystem;
    List<String> errors;
    
    CardValidationInfo(boolean isValid, List<String> errors, Optional<PaymentSystem> paymentSystem) {
    	this.isValid = isValid;
    	this.errors = errors;
    	this.paymentSystem = paymentSystem;
    }
    
    public boolean getIsValid() {
    	return isValid;
    }
    
    public Optional<PaymentSystem> getPaymentSystem() {
        return paymentSystem;
    }
    
    public List<String> getErrors() {
    	return errors;
    }
}
