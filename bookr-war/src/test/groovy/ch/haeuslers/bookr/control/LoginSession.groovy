package ch.haeuslers.bookr.control

import ch.haeuslers.bookr.JBossLoginContextFactory

import javax.security.auth.Subject
import javax.security.auth.login.LoginContext
import java.security.PrivilegedAction

class LoginSession {

    private LoginContext loginContext

    static LoginSession loginAsAdministrator() {
        loginAs('administrator', 'administrator')
    }

    static LoginSession loginAsUser() {
        loginAs('user', 'user')
    }

    static LoginSession loginAsManager() {
        loginAs('manager', 'manager')
    }

    static LoginSession loginAs(String username, String password) {
        return new LoginSession(JBossLoginContextFactory.createLoginContext(username, password))
    }

    private LoginSession(LoginContext loginContext) {
        this.loginContext = loginContext
        loginContext.login();
    }

    def logout() {
        loginContext.logout()
    }

    def call = {Closure action ->
        Subject.doAs(loginContext.subject, new PrivilegedAction() {
            @Override
            Object run() {
                action();
            }
        })
    }
}
