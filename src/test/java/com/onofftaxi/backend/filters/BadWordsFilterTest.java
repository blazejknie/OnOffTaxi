package com.onofftaxi.backend.filters;

import org.junit.Before;
import org.junit.Test;

import javax.swing.text.html.Option;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class BadWordsFilterTest {

    private BadWordsFilter wordsFilter;

    @Before
    public void setUp() throws IOException {
        wordsFilter = new BadWordsFilter();
    }

    @Test
    public void isChecked() {
        assertTrue(wordsFilter.isChecked("NotAVulgarism"));
    }

    @Test
    public void notChecked() {
        assertFalse(wordsFilter.isChecked("kurwa"));
    }

    @Test
    public void getWordsList() {
        Method[] methods = BadWordsFilter.class.getMethods();
        boolean getWordsList = false;
        for (Method m : methods) {
            getWordsList = m.equals("getWordsList");
        }
        assertNotNull(getWordsList);
    }

    @Test
    public void getWordsFilter() {
        List<String> method = BadWordsFilter.getWordsList();

        assertNotNull(method);

        assertFalse(method.isEmpty());
    }

    @Test
    public void should_return_optional_empty() {
        Optional<FileReader> optionalFile = wordsFilter.getOptionalFile("src\\main\\resources\\static\\notfound.txt");

        Optional<FileReader> empty = Optional.empty();

        assertEquals(empty, optionalFile);
    }

    @Test
    public void should_return_optional() {
        Optional<FileReader> optionalFile = wordsFilter.getOptionalFile("src\\main\\resources\\static\\badwords.txt");

        Optional<FileReader> empty = Optional.empty();

        assertNotEquals(empty, optionalFile);

    }
}
