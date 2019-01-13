package rf.mat.mod;

import java.io.*;
import java.nio.file.*;
import java.nio.charset.*;

public class ConfigUtil
{
    private static File CONFIG_FILE;
    
    public static void setConfigFile() {
        try {
            if (!ConfigUtil.CONFIG_FILE.exists()) {
                ConfigUtil.CONFIG_FILE.createNewFile();
            }
            final BufferedWriter bw = new BufferedWriter(new FileWriter(ConfigUtil.CONFIG_FILE));
            bw.write(MatMod.apikey);
            bw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getApiKey() {
        if (!ConfigUtil.CONFIG_FILE.exists()) {
            setConfigFile();
        }
        try {
            final String apiKeyString = new String(Files.readAllBytes(ConfigUtil.CONFIG_FILE.toPath()), StandardCharsets.UTF_8);
            return apiKeyString;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return MatMod.apikey;
    }
    
    static {
        ConfigUtil.CONFIG_FILE = new File(MatMod.getInstance().getMinecraft().mcDataDir + "/config/matmod_hypixelapikey.cfg");
    }
}
