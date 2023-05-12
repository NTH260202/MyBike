package com.swd.bike.core;


import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Registry {
    private static final Map<Class<? extends BaseRequestData>, RequestHandler> COMMAND_HANDLER_MAP = new HashMap<>();
    private final ApplicationContext applicationContext;

    public Registry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.initCommandHandlerBeans();
    }

    private void initCommandHandlerBeans() {
        String[] commandHandlerBeanNames = this.applicationContext.getBeanNamesForType(RequestHandler.class);
        String[] var2 = commandHandlerBeanNames;
        int var3 = commandHandlerBeanNames.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String commandHandlerBeanName = var2[var4];
            this.initCommandHandlerBean(commandHandlerBeanName);
        }

    }

    private void initCommandHandlerBean(String commandHandlerBeanName) {
        Class<?> handlerClass = this.applicationContext.getType(commandHandlerBeanName);
        Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(handlerClass, RequestHandler.class);
        Class<?> commandType = generics[0];
        COMMAND_HANDLER_MAP.put((Class<? extends BaseRequestData>) commandType, (RequestHandler)this.applicationContext.getBean(commandHandlerBeanName));
    }

    public <R extends BaseRequestData> RequestHandler getCommandHandler(Class<R> requestCommandClass) {
        RequestHandler commandHandler = (RequestHandler)COMMAND_HANDLER_MAP.get(requestCommandClass);
        return (RequestHandler)(commandHandler == null ? UnsupportedRequestHandler.getInstance() : commandHandler);
    }

}