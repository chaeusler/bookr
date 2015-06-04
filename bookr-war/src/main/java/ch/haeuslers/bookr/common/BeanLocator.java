package ch.haeuslers.bookr.common;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public final class BeanLocator {

    private BeanLocator() {
        // utilities
    }

    public static <T> T lookup(final Class<T> type, final String jndiName) {
        final Object bean = lookup(jndiName);
        return type.cast(PortableRemoteObject.narrow(bean, type));
    }

    public static Object lookup(final String jndiName) {
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
