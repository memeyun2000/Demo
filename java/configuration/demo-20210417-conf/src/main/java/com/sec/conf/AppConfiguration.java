package com.sec.conf;

import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ConfigurationNode;

import java.net.URL;
import java.util.List;

/**
 * Hello world!
 */
public class AppConfiguration extends XMLConfiguration {
    public static void main(String args[]) throws Exception{
    }


    public static AppConfiguration conf;
    public static String CONFIG_SITE = "config-site.xml";

    public AppConfiguration create() throws Exception{
        if (conf != null) {
            return conf;
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL url;

        url = AppConfiguration.class.getResource(CONFIG_SITE);
        if (url == null) {
            ClassLoader cl = AppConfiguration.class.getClassLoader();
            if (cl != null) {
                url = cl.getResource(CONFIG_SITE);
            }
        }
        if (url == null) {
            url = classLoader.getResource(CONFIG_SITE);
        }

        if (url == null) {
            System.out.println("Failed to load configuration, proceeding with a default");
            conf = new AppConfiguration();
        } else {
            conf = new AppConfiguration(url);
        }


        return conf;
    }

    public AppConfiguration(){

    }
    public AppConfiguration(URL url) throws Exception{
        load(url);
    }

    public static enum ConfVars {
        AUTHOR_NAME("author.name", "guoqingyun"),
        CONFIG_SITE("config.site", "config-site.xml");

        private String varName;
        private String stringValue;

        private VarType type;

        ConfVars(String varName, String stringValue) {
            this.varName = varName;
            this.stringValue = stringValue;
            this.type = VarType.STRING;
        }

        public String getVarName() {
            return this.varName;
        }
        public String getVarValue() {
            return this.stringValue;
        }



        enum VarType{
        STRING,INT,FLOAT,LONG,BOOLEAN
        }

    }

    public String getString(ConfVars c) {
        return getString(c.name(),c.getVarName(),c.getVarValue());
    }

    public String getString(String envName, String propertyName, String defaultValue) {
        if (System.getenv(envName) != null) {
            return System.getenv(envName);
        }
        if (System.getProperty(propertyName) != null) {
            return System.getProperty(propertyName);
        }

        return getStringValue(propertyName, defaultValue);
    }
    private String getStringValue(String name, String d) {
        List<ConfigurationNode> properties = getRootNode().getChildren();
        if (properties == null || properties.size() == 0) {
            return d;
        }
        for (ConfigurationNode p : properties) {
            if (p.getChildren("name") != null && p.getChildren("name").size() > 0
                    && name.equals(p.getChildren("name").get(0).getValue())) {
                return (String) p.getChildren("value").get(0).getValue();
            }
        }
        return d;
    }
}
