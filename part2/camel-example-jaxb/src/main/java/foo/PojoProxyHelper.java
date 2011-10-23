package foo;

import java.lang.reflect.Proxy;

import org.apache.camel.Endpoint;
import org.apache.camel.Producer;
import org.apache.camel.util.ServiceHelper;

public class PojoProxyHelper {
    /**
     * Creates a Proxy which sends the exchange to the endpoint.
     */
    public static Object createProxyObject(Endpoint endpoint, Producer producer, ClassLoader classLoader, Class[] interfaces) {
        return Proxy.newProxyInstance(classLoader, interfaces.clone(), new PojoMessageInvocationHandler(endpoint, producer));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Endpoint endpoint, ClassLoader cl, Class[] interfaces) throws Exception {
        Producer producer = endpoint.createProducer();
        // ensure the producer is started
        ServiceHelper.startService(producer);
        return (T) createProxyObject(endpoint, producer, cl, interfaces);
    }
    
	public static <T> T createProxy(Endpoint endpoint, Class<?>... interfaceClasses) throws Exception {
        return (T) createProxy(endpoint, getClassLoader(interfaceClasses), interfaceClasses);
    }
	
    /**
     * Returns the class loader of the first interface or throws {@link IllegalArgumentException} if there are no interfaces specified
     */
    protected static ClassLoader getClassLoader(Class<?>... interfaces) {
        if (interfaces == null || interfaces.length < 1) {
            throw new IllegalArgumentException("You must provide at least 1 interface class.");
        }
        return interfaces[0].getClassLoader();
    }
}
