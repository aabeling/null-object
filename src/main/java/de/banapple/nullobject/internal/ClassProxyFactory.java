package de.banapple.nullobject.internal;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.NoOp;

public class ClassProxyFactory<T>
    implements IProxyFactory<T>
{
    private Class<T> type;
    private InvocationHandler handler;
    
    public ClassProxyFactory(Class<T> type)
    {
        this.type = type;
    }
    
    public ClassProxyFactory(Class<T> type, InvocationHandler handler)
    {
        this.type = type;
        this.handler = handler;
    }
    
    @SuppressWarnings("unchecked")
    public T createProxy()
    {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(type);
                
        Callback callback = handler;
        if ( handler == null ) {
            callback = NoOp.INSTANCE;
        }
        
        enhancer.setCallback(callback);
        return (T) enhancer.create();
    }
    
}