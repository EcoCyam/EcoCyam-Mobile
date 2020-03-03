package com.example.ecocyam.language_decorator;

import com.example.ecocyam.interfaces.AllLanguage;

import java.util.Map;

public class FrenchLanguage extends LanguageDecorator {

    public FrenchLanguage(AllLanguage language) {
        super(language);
    }

    @Override
    public Map<String, String> addSupportedLanguage() {
        return addFrenchLanguage(super.addSupportedLanguage());
    }

    private Map<String, String> addFrenchLanguage(Map<String, String> previousLanguage) {
        previousLanguage.put("fr","French");
        return previousLanguage;
    }
}
