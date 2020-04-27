package com.yiwen.httpserver.core;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;

public class HttpConnectionWorkerThread extends Thread {
    private final static Logger LOGGER= LoggerFactory.getLogger(HttpConnectionWorkerThread.class);
    private Socket socket;
    public int [][] projectdata={{1,1,1,0,0},{2,3,4,0,0},{3,5,5,0,0},{4,6,2,0,0},{5,7,10,0,0}};

    public HttpConnectionWorkerThread(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        InputStream inputStream=null;
        OutputStream outputStream=null;
        byte a;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //PrintWriter pw =new PrintWriter(outputStream);
            //pw.println(allocateSpace(projectdata));
            //pw.flush();

            String html="haha";
            final String CRLF = "\r\n";

            String response =
                    "HTTP/1. 1 200 OK" + CRLF + //status line:HTTP version response code response message
                            "Content-Length: " + html.getBytes().length + CRLF +//
                            CRLF +
                            html +
                            CRLF + CRLF;


            //BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            //JSONObject json = new JSONObject(in.readLine());
            outputStream.write(response.getBytes());
            //outputStream.write(allocateSpace(projectdata).getBytes().);



            LOGGER.info("Connection processing Finished");
        }catch (IOException e){
            LOGGER.error("Problem with communication",e);
            e.printStackTrace();
        }finally {
            if(inputStream!= null){
                try {
                    inputStream.close();
                } catch (IOException e) {

                }
            }
            if(outputStream!=null) {

                try {
                    outputStream.close();
                } catch (IOException e) {

                }
            }
            if(socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {

                }
            }

        }

    }
    public static String allocateSpace(int[][] projectdata){
        final int[][] spacedata=new int[][]{{1,1,1,0,0},{2,3,4,0,0},{3,5,5,0,0},{4,6,2,0,0},{5,7,10,0,0}};
        int []store;
        String output = "";
        for(int i=projectdata.length-1;i>=0;i--){
            for(int j=1;j<=i;j++) {

                if (projectdata[j-1][1] * projectdata[j-1][2] <= projectdata[j][1] * projectdata[j][2]) {
                    store=projectdata[j-1];
                    projectdata[j-1]=projectdata[j];
                    projectdata[j]=store;
                }
            }

        }
        int[][] copy = new int[spacedata.length][spacedata[0].length];
        for (int i = 0; i < copy.length; i++)
            copy[i] = Arrays.copyOf(spacedata[i], spacedata[i].length);

        for(int i =0;i<projectdata.length;i++){
            for(int j=0;j<copy.length;j++) {
                if (projectdata[i][1] <= copy[j][1] & projectdata[i][2] <= copy[j][2]) {
                    projectdata[i][0] = spacedata[j][0];
                    copy[j][1] = -999;
                    copy[j][2] = -999;

                    spacedata[j][3]=projectdata[i][3];
                    break;
                }
                if(j+1==copy.length&&(projectdata[i][1] > copy[j][1] || projectdata[i][2] > copy[j][2])){
                    projectdata[i][4]=1;
                }

            }

        }
        for(int i =0;i<projectdata.length;i++){

        }
        return output;


    }
}
