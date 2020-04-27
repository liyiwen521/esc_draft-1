package com.yiwen.httpserver;

import com.sun.net.httpserver.HttpServer;
import com.yiwen.httpserver.config.Configuration;
import com.yiwen.httpserver.config.ConfigurationManager;
import com.yiwen.httpserver.core.ServerListenerThread;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class httpserver {
    private final static Logger LOGGER= LoggerFactory.getLogger(HttpServer.class);
    public static void main(String[] args) {
        LOGGER.info("Server Starting..");
        ConfigurationManager.getInstance().loadConfigurationFile("src/main/resources/http.json");
        Configuration conf=ConfigurationManager.getInstance().getCurrentConfiguration();
        LOGGER.info("Using port:"+conf.getPort());
        LOGGER.info("Using Webroot:"+conf.getWebroot());
        ServerListenerThread serverListenerThread= null;
        try {
            serverListenerThread = new ServerListenerThread(conf.getPort(),conf.getWebroot());
            serverListenerThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
