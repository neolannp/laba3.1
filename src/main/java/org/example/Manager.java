package org.example;

import org.example.handlers.JSONHandler;
import org.example.handlers.XMLHandler;
import org.example.handlers.YAMLHandler;

import java.util.HashMap;

public class Manager {

    public HashMap<String, Reactor> readCommonClass(String filePath) {
        JSONHandler jsonHandler = new JSONHandler();
        XMLHandler xmlHandler = new XMLHandler();
        YAMLHandler yamlHandler = new YAMLHandler();

        yamlHandler.setNextFileHandler(xmlHandler);
        xmlHandler.setNextFileHandler(jsonHandler);

        HashMap<String, Reactor> reactors = yamlHandler.selectivelyLoadReactors(filePath);
        return reactors;
    }
}
