package com.zhuke.smart_home.listener;

import com.google.common.base.Stopwatch;
import com.zhuke.smart_home.service.DeviceService;
import com.zhuke.smart_home.service.impl.DeviceServiceImpl;
import com.zhuke.smart_home.service.impl.ServerConnServiceImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZHUKE on 2016/3/8.
 */
public class SHListener extends ContextLoaderListener {
    private static Logger logger = LogManager.getLogger(SHListener.class);

    @Override
    public void contextInitialized(javax.servlet.ServletContextEvent event) {
        try {
            super.contextInitialized(event);
            ApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());

            ServerConnServiceImpl serverConnService = applicationContext.getBean(ServerConnServiceImpl.class);
            ThreadPoolExecutor threadPoolExecutor = applicationContext.getBean(ThreadPoolExecutor.class);
            threadPoolExecutor.execute(serverConnService);

            DeviceService deviceStatusInitService = applicationContext.getBean(DeviceService.class);
            deviceStatusInitService.initDeviceStatus();

        } catch (Exception e) {
            logger.error("Start server occured exception", e);
        }
    }
}