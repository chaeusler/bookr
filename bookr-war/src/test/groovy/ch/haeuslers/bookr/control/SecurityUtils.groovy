package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory

import javax.security.auth.Subject
import javax.security.auth.login.LoginContext
import java.security.PrivilegedAction

class SecurityUtils {

    static LoginContext loginAsAdministrator() {
        loginAs('administrator', 'administrator')
    }

    static LoginContext loginAsUser() {
        loginAs('user', 'user')
    }

    static LoginContext loginAs(String username, String password) {
        LoginContext loginContext = JBossLoginContextFactory.createLoginContext(username, password)
        loginContext.login()
        return loginContext
    }

    static def doWith = {LoginContext loginContext, Closure action ->
        Subject.doAs(loginContext.subject, new PrivilegedAction() {
            @Override
            Object run() {
                action();
            }
        })
    }
}
