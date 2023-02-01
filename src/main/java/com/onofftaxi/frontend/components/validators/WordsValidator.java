package com.onofftaxi.frontend.components.validators;

import com.onofftaxi.backend.filters.BadWordsFilter;
import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.AbstractValidator;

import java.io.IOException;
import java.util.Optional;

public class WordsValidator extends AbstractValidator<String> {

    private String errorMessage;
    private BadWordsFilter badWordsFilter;

    public WordsValidator(String errorMessage) throws IOException {
        super(errorMessage);
        this.errorMessage = errorMessage;
        badWordsFilter = new BadWordsFilter();
    }

    @Override
    public ValidationResult apply(String s, ValueContext valueContext) {

        if (getBadWords().isPresent()){
            if (getBadWords().get().isChecked(s)){
                return ValidationResult.ok();
            }else
                return ValidationResult.create(errorMessage,ErrorLevel.ERROR);
        }
        return ValidationResult.error("Filter not found");
    }

    private Optional<BadWordsFilter> getBadWords(){
        return Optional.of(badWordsFilter);
    }
}
