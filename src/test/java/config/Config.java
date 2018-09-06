package config;

/**
 * Help class to get passed parameters from environment for further usage in the automation project
 */
public class Config {
    private static final String DEFAULT_BASE_URL = "http://prestashop-automation.qatestlab.com.ua/ru/";
    private static final String DEFAULT_BASE_ADMIN_URL = "http://prestashop-automation.qatestlab.com.ua/admin147ajyvk0/";
    private static final String USERNAME = "webinar.test@gmail.com";
    private static final String PASSWORD = "Xcg7299bnSmMuRLp9ITw";

    /**
     * @return ULR of the website.
     */
    public static String getBaseUrl() {
        return System.getProperty(EnvironmentVariables.BASE_URL.toString(), DEFAULT_BASE_URL);
    }

    /**
     * @return Username to login.
     */
    public static String getUsername() {
        return System.getProperty(EnvironmentVariables.USERNAME.toString(), USERNAME);
    }
    /**
     * @return Password to login.
     */
    public static String getPassword() {
        return  System.getProperty(EnvironmentVariables.PASSWORD.toString(), PASSWORD);
    }

    /**
     * @return ULR of the Admin Panel.
     */
    public static String getBaseAdminUrl() {
        return System.getProperty(EnvironmentVariables.BASE_ADMIN_URL.toString(), DEFAULT_BASE_ADMIN_URL);
    }
}

/**
 * Parameters that are passed to automation project
 */
enum EnvironmentVariables {
    BASE_URL("env.url"),
    BASE_ADMIN_URL("env.admin.url"),
    USERNAME("user"),
    PASSWORD("pass");

    private String value;
    EnvironmentVariables(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
