package com.onofftaxi.frontend.components.validators;

import com.vaadin.flow.data.binder.ErrorLevel;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.*;

public class WordsValidatorTest {

    private final String TEST = "Its not a vulgarism";

    @Mock
    ValueContext valueContext;

    private WordsValidator wordsValidator;

    @Before
    public void setUp() throws IOException {
        wordsValidator = new WordsValidator("not ok");
    }

    @Test
    public void should_return_validation_ok() throws IOException {

        Optional<ErrorLevel> expected = ValidationResult.ok().getErrorLevel();

        assertEquals(expected, wordsValidator.apply(TEST, valueContext).getErrorLevel());
    }

    @Test
    public void apply_error_message() throws IOException {
        assertSame(wordsValidator.apply("chuj", valueContext).getErrorMessage(),
                wordsValidator.apply("kurwa", valueContext).getErrorMessage());
    }
}
