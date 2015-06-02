package ch.haeuslers.bookr.core.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public class BeanLocator {

    public static <T> T lookup(Class<T> type, String jndiName) {
        Object bean = lookup(jndiName);
        return type.cast(PortableRemoteObject.narrow(bean, type));
    }

    public static Object lookup(String jndiName) {
        Context contex = null;
        try {
            contex = new InitialContext();
            return contex.lookup(jndiName);
        } catch (NamingException e) {
            throw new IllegalStateException(e);
        } finally {
            try {
                if (contex != null) {
                    contex.close();
                }
            } catch (NamingException e) {
                // Thrown before. So we know it.
            }
        }
    }
}
