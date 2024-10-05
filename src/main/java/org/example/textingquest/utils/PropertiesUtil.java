package org.example.textingquest.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PropertiesUtil {

    private static final Properties PROPERTIES=new Properties();
   static {
           loadProperties();
   }

    private static void loadProperties()  {

      try(var inputStream= PropertiesUtil.class.getClassLoader()
               .getResourceAsStream("application.properties"))
      {

          PROPERTIES.load(inputStream);
      }catch (IOException e)
      {
          throw new RuntimeException();
      }
    }

    public static String get(String key)
    {
        return PROPERTIES.getProperty(key);
    }

}
