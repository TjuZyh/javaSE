package com.javaSE.set;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            Properties properties = new Properties();
             fileInputStream = new FileInputStream("src/com/javaSE/set/user.properties");
            //加载对应文件
            properties.load(fileInputStream);

            String name = properties.getProperty("name");
            String password = properties.getProperty("password");

            System.out.println(name + ": " + password);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
