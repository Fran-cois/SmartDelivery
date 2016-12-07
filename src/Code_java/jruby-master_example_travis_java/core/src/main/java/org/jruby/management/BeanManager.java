package org.jruby.management;

import org.jruby.compiler.JITCompilerMBean;

public interface BeanManager {

    void register(JITCompilerMBean jitCompiler);

    void register(ConfigMBean config);

    void register(ParserStatsMBean parserStats);

    void register(MethodCacheMBean methodCache);

    void register(Runtime runtime);

    void unregisterCompiler();

    void unregisterConfig();

    void unregisterMethodCache();

    void unregisterParserStats();
    
    void unregisterRuntime();

    /**
     * Attempt to shut down the current JVM's JMX agent. Uses reflection tricks,
     * so it may fail; return value indicates if it was successful.
     *
     * @return true if successful; false otherwise
     */
    boolean tryShutdownAgent();

    /**
     * Attempt to restart the current JVM's JMX agent. May fail, so the return value indicates success.
     *
     * @return true if successful; false otherwise
     */
    boolean tryRestartAgent();

}
