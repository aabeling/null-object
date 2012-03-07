package de.banapple.nullobject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NullSafeTest
{
    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    @Test
    public void testMethodCallOnNullSafeObject()
    {
        FooBar object = NullSafe.create(FooBar.class);
        object.method1();
    }
    
    @Test
    public void testMethodCallOnNullSafeObjectWithBuilder()
    {
        FooBar object = 
            NullSafe.createBuilder(FooBar.class).onMethod("method1").create();
        object.method1();
    }
    
    @Test
    public void testMethodCallOnUnsafeMethod()
    {
        exception.expect(NullPointerException.class);
        
        FooBar object = 
            NullSafe.createBuilder(FooBar.class).onMethod("method1").create();
        object.method2();
    }
    
    @Test
    public void testUnknownMethod()
    {
        exception.expect(IllegalArgumentException.class);
        
        NullSafe.createBuilder(FooBar.class).onMethod("foobar").create();
    }
}
