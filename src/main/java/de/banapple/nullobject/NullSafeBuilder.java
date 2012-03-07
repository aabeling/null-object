package de.banapple.nullobject;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import net.sf.cglib.proxy.InvocationHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.banapple.nullobject.internal.ClassProxyFactory;
import de.banapple.nullobject.internal.IProxyFactory;

/**
 * Builder for null objects.
 *
 * @author <a href="mailto:achim.abeling@freenet-ag.de">Achim Abeling</a>
 */
public class NullSafeBuilder<T>
{
    private static Logger log = LoggerFactory.getLogger(NullSafeBuilder.class);
    
    private Class<T> type;
    private Set<Method> nullSafeMethods;
    
    NullSafeBuilder(Class<T> type)
    {
        this.type = type;
        nullSafeMethods = new HashSet<Method>();
    }
    
    /**
     * Returns the generated null safe object.
     * 
     * @return
     */
    public T create()
    {
        IProxyFactory<T> proxyFactory = getProxyFactory(type);
        T result = proxyFactory.createProxy();
        return result;
    }
    
    /**
     * <p>
     * Declares the given method as being null-safe.
     * </p>
     * 
     * <p>
     * All methods with this name are made null-safe.
     * </p>
     * 
     * @param methodName
     * @return
     */
    public NullSafeBuilder<T> onMethod(String methodName)
    {
        /* find matching method */
        boolean isMethodFound = false;
        for (Method checkMethod : type.getDeclaredMethods()) {
            if ( checkMethod.getName().equals(methodName) ) {
                nullSafeMethods.add(checkMethod);
                log.info("added null safe method: {}", checkMethod);
                isMethodFound = true;
            }
        }
        
        if ( !isMethodFound ) {
            throw new IllegalArgumentException("method not found: " + methodName);
        }
        
        return this;
    }
    
    @SuppressWarnings("unchecked")
    IProxyFactory<T> getProxyFactory(Class<T> type)
    {
        if ( nullSafeMethods.size() == 0 ) {
            return new ClassProxyFactory(type);
        } else {
            InvocationHandler handler = new InvocationHandler() {                
                public Object invoke(Object proxy, Method method, Object[] args)
                        throws Throwable
                {
                    log.info("invoked method: {}", method);
                    if ( nullSafeMethods.contains(method) ) {
                        return null;
                    } else {
                        /* the current method is not null safe */
                        log.info("invoked method is not null safe: {}", method);
                        throw new NullPointerException();
                    }
                }
            };
            return new ClassProxyFactory(type, handler);
        }
    }
}
