package com.example.ecocyam.language_decorator;

import com.example.ecocyam.interfaces.AllLanguage;

import java.util.Map;

public abstract class LanguageDecorator implements AllLanguage {
    protected AllLanguage allLanguage;

    public LanguageDecorator(AllLanguage language) {
        allLanguage= language;
    }

    @Override
    public Map<String, String> addSupportedLanguage() {
        return allLanguage.addSupportedLanguage();
    }
}
