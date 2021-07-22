package core;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import com.example.poclogincognitoandroid.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    static Properties properties;

    public static String getConfigValue(Context context, String name) {
        Resources resources = context.getResources();
        try {
            if (properties == null) {
                InputStream rawResource = resources.openRawResource(R.raw.config);
                properties = new Properties();
                properties.load(rawResource);
            }
            return properties.getProperty(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}