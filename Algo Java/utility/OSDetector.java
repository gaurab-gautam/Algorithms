/**
 *
 * @author Gaurab R. Gautam
 */
package utility;

public class OSDetector {
    public static enum OS
    {
        UNIX ("Unix"),
        LINUX ("Linux"),
        MACINTOSH ("Macintosh"),
        SOLARIS ("Solaris"),
        WINDOWS ("Windows"),
        UNKNOWN ("Unknown");
        
        private final String name;
        
        OS (String name)
        {
            this.name = name;
        }
        
        @Override
        public String toString()
        {
            return this.name;
        }
    }
    
    public static OS getOS()
    {
        return detectOS();
    }
    
    private static OS detectOS()
    {
        String osName = System.getProperty("os.name").toLowerCase();
        
        if (osName.contains("win"))
        {
            return OS.WINDOWS;
        }
        else if (osName.contains("mac"))
        {
            return OS.MACINTOSH;
        }
        else if (osName.contains("unix") || osName.contains("aix"))
        {
            return OS.UNIX;
        }
        else if (osName.contains("linux"))
        {
            return OS.LINUX;
        }
        else if (osName.contains("sunos"))
        {
            return OS.SOLARIS;
        }
        else
        {
            return OS.UNKNOWN;
        }
    }
    
}
