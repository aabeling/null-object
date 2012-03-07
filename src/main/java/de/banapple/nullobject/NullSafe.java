package de.banapple.nullobject;

/**
 * <p>
 * Allows to create null safe objects.
 * </p>
 * 
 * <p>
 * For more information about this pattern see
 * http://en.wikipedia.org/wiki/Null_Object_pattern
 * </p>
 * 
 * @author <a href="mailto:achim.abeling@freenet-ag.de">Achim Abeling</a>
 */
public class NullSafe    
{

    /**
     * Creates a null object which is safe for all methods of the
     * given type.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> T create(Class<T> type)
    {
        return createBuilder(type).create();
    }
    
    /**
     * Creates a builder for a null object of the given type.
     * 
     * @param <T>
     * @param type
     * @return
     */
    public static <T> NullSafeBuilder<T> createBuilder(Class<T> type)
    {
        return new NullSafeBuilder<T>(type);
    }
    
}
