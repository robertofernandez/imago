package ar.com.sodhium.commons.files.persistence;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

//import org.apache.commons.io.output.FileWriterWithEncoding;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class ObjectPersistenceManager {

    public Object readFile(Type classInput, String filePath) throws FileNotFoundException {
        Gson gson = createGson();
        JsonReader reader = new JsonReader(new FileReader(filePath));
        return gson.fromJson(reader, classInput);
    }

    public List<Object> readFolder(Type classInput, String folderPath) throws FileNotFoundException {
        ArrayList<Object> output = new ArrayList<>();
        Gson gson = createGson();
        File containingFolder = new File(folderPath);
        for (String fileName : containingFolder.list()) {
            JsonReader reader = new JsonReader(new FileReader(folderPath + "/" + fileName));
            output.add(gson.fromJson(reader, classInput));
        }
        return output;
    }

    public void writeFileWithDefaultEncoding(Object element, String filePath) throws IOException {
        try (Writer writer = new FileWriter(filePath)) {
            Gson gson = createGson();
            gson.toJson(element, writer);
        }
    }

    public void writeFile(Object element, String filePath) throws IOException {
        writeFileUTF8(element, filePath);
    }

    public void writeFileUTF8(Object element, String filePath) throws IOException {
    	/*
        try (Writer writer = new FileWriterWithEncoding(filePath, Charset.forName("UTF-8"))) {
            Gson gson = createGson();
            gson.toJson(element, writer);
        }*/
    }

    private Gson createGson() {
        return new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();
    }

}
