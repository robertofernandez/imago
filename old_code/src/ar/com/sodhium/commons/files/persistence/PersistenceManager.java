package ar.com.sodhium.commons.files.persistence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.io.output.FileWriterWithEncoding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class PersistenceManager<T> {

    public T readFile(Type classInput, String filePath, String dateFormat) throws FileNotFoundException {
        Gson gson = createGson(dateFormat);
        JsonReader reader = new JsonReader(new FileReader(filePath));
        return gson.fromJson(reader, classInput);
    }

    public T readFile(Type classInput, String filePath) throws FileNotFoundException {
        Gson gson = createGson();
        InputStreamReader sr = new InputStreamReader(new FileInputStream(filePath), Charset.forName("UTF-8"));
        BufferedReader br = new BufferedReader(sr);
        JsonReader reader = new JsonReader(br);
        return gson.fromJson(reader, classInput);
    }

    public List<T> readFolder(Type classInput, String folderPath) throws FileNotFoundException {
        ArrayList<T> output = new ArrayList<>();
        Gson gson = createGson();
        File containingFolder = new File(folderPath);
        for (String fileName : containingFolder.list()) {
            InputStreamReader sr = new InputStreamReader(new FileInputStream(folderPath + "/" + fileName), Charset.forName("UTF-8"));
            BufferedReader br = new BufferedReader(sr);
            JsonReader reader = new JsonReader(br);
            output.add(gson.fromJson(reader, classInput));
        }
        return output;
    }

    public void writeFileWithDefaultCharset(T element, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = createGson();
            gson.toJson(element, writer);
        }
    }

    public void writeFile(T element, String filePath) throws IOException {
        writeFileUTF8(element, filePath);
    }

    public void writeFileUTF8(T element, String filePath) throws IOException {
    	/*
        try (Writer writer = new FileWriterWithEncoding(filePath, Charset.forName("UTF-8"))) {
            Gson gson = createGson();
            gson.toJson(element, writer);
        }*/
    }

    private Gson createGson() {
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

    private Gson createGson(String dateFormat) {
        return new GsonBuilder().setPrettyPrinting().setDateFormat(dateFormat).excludeFieldsWithoutExposeAnnotation()
                .create();
    }

}
