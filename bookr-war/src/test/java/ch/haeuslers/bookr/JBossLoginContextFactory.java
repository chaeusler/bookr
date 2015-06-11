package ch.haeuslers.bookr;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * https://github.com/sfcoy/demos/blob/master/arquillian-security-demo/src/test/java/org/jboss/arquillian/secureejb/JBossLoginContextFactory.java
 *
 *
 * Provides a {@link LoginContext} for use by unit tests. It is driven by users.properties and roles.properties files as
 * described in <a href="https://community.jboss.org/wiki/UsersRolesLoginModule">UsersRolesLoginModule</a>
 *
 * ensure standalone.xml contains:
 *
 * <subsystem xmlns="urn:jboss:domain:security:1.2">
 * <security-domains>
 * <security-domain name="testing" cache-type="default">
 * <authentication>
 * <login-module code="UsersRoles" flag="sufficient"/>
 * </authentication>
 * </security-domain>
 */
public class JBossLoginContextFactory {
    static class NamePasswordCallbackHandler implements CallbackHandler {
        private final String username;
        private final String password;

        private NamePasswordCallbackHandler(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (Callback current : callbacks) {
                if (current instanceof NameCallback) {
                    ((NameCallback) current).setName(username);
                } else if (current instanceof PasswordCallback) {
                    ((PasswordCallback) current).setPassword(password.toCharArray());
                } else {
                    throw new UnsupportedCallbackException(current);
                }
            }
        }
    }

    static class JBossJaasConfiguration extends Configuration {
        private final String configurationName;

        JBossJaasConfiguration(String configurationName) {
            this.configurationName = configurationName;
        }

        @Override
        public AppConfigurationEntry[] getAppConfigurationEntry(String name) {
            if (!configurationName.equals(name)) {
                throw new IllegalArgumentException("Unexpected configuration name '" + name + "'");
            }

            return new AppConfigurationEntry[]{

                createUsersRolesLoginModuleConfigEntry(),

                createClientLoginModuleConfigEntry(),

            };
        }

        /**
         * The {@link org.jboss.security.auth.spi.UsersRolesLoginModule} creates the association between users and
         * roles.
         *
         * @return
         */
        private AppConfigurationEntry createUsersRolesLoginModuleConfigEntry() {
            Map<String, String> options = new HashMap<>();
            return new AppConfigurationEntry("org.jboss.security.auth.spi.UsersRolesLoginModule",
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
        }

        /**
         * The {@link org.jboss.security.ClientLoginModule} associates the user credentials with the
         * {@link org.jboss.security.SecurityContext} where the JBoss security runtime can read it.
         *
         * @return
         */
        private AppConfigurationEntry createClientLoginModuleConfigEntry() {
            Map<String, String> options = new HashMap<>();
            options.put("multi-threaded", "true");
            options.put("restore-login-identity", "true");

            return new AppConfigurationEntry("org.jboss.security.ClientLoginModule",
                AppConfigurationEntry.LoginModuleControlFlag.REQUIRED, options);
        }
    }

    /**
     * Obtain a LoginContext configured for use with the ClientLoginModule.
     *
     * @return the configured LoginContext.
     */
    public static LoginContext createLoginContext(final String username, final String password) throws LoginException {
        final String configurationName = "Arquillian Testing";

        CallbackHandler cbh = new JBossLoginContextFactory.NamePasswordCallbackHandler(username, password);
        Configuration config = new JBossJaasConfiguration(configurationName);

        return new LoginContext(configurationName, new Subject(), cbh, config);
    }
}

