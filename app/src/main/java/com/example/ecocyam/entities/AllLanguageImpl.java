package com.example.ecocyam.entities;

import com.example.ecocyam.interfaces.AllLanguage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AllLanguageImpl implements AllLanguage { //default language

    @Override
    public Map<String, String> addSupportedLanguage() {
       Map<String,String> enLanguage = new ConcurrentHashMap<>();
       enLanguage.put("en_US","English");
       return enLanguage;
    }
}
