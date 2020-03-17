package com.deloitte.diway.weatherapp.mock.transformer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResponseTransformer {

    public static String readAllBytes(String filePath)
    {
        String content = "";
        try
        {
            content = new String ( Files.readAllBytes( Paths.get(filePath) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return content;
    }
}
