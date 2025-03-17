package dev.andrylat.raqimbek.bankingutils;

import java.util.List;

public class CardValidationInfo {
    private boolean isValid;
    private List<String> errors;
    
    CardValidationInfo(boolean isValid, List<String> errors) {
    	this.isValid = isValid;
    	this.errors = errors;
    }
    
    public boolean getIsValid() {
    	return isValid;
    }
    
    public List<String> getErrors() {
    	return errors;
    }
}
