package com.demon.example.startup;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.container.spring.SpringContainer;

/**
 * 业务启动类
 */
@Component
public class Main extends DaemonJob {

    private static final String SPRING_CONTEXT_PATH = "classpath:spring/spring-context.xml";
    private static final String QUARTZ_PATH = "classpath:spring/spring-quartz-example.xml";
//    private static final String VMCACHE_QUARTZ_PATH = "classpath:spring/spring-quartz-vmcache.xml";
    
//    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static ClassPathXmlApplicationContext context;
    
    public static void main(String[] args) throws Exception {
        bootstrap();
    }

    @Override
    public void start() throws Exception {
        bootstrap();
    }

    private static void bootstrap(){
        context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_PATH, QUARTZ_PATH);
        
        String [] args = new String[0];
        System.setProperty(SpringContainer.SPRING_CONFIG, SPRING_CONTEXT_PATH);
        com.alibaba.dubbo.container.Main.main(args);
    }
    
    @Override
    public void destroy() {
        super.destroy();
        context.destroy();
    }
    
}
