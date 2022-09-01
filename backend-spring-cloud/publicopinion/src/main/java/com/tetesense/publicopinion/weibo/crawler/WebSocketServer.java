package com.tetesense.publicopinion.weibo.crawler;


import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.stream.Collectors;

/**
 * WebSocket 服务类
 **/
@ServerEndpoint("/websocket/{sid}")
//@PropertySource("classpath:paths.yml")
@Component
public class WebSocketServer {
    private String pythonPath = SpringUtil.getApplicationContext().getEnvironment().getProperty("pythonPath");
    private String startPath = SpringUtil.getApplicationContext().getEnvironment().getProperty("startPyPath");

    static Log log = LogFactory.getLog(WebSocketServer.class);
    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent 包的线程安全 Set ，用来存放每个客户端对应的 MyWebSocket 对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 接收 sid
    private String sid = "";

    // 创建一个数组用来存放所有需要向客户端发送消息的窗口号
    private static List<String> list = new ArrayList();


    public static List<String> getList() {
        return list;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        this.session = session;
        this.sid = sid;
        list.add(sid);
        webSocketSet.add(this);
        addOnlineCount();   //在线数加1
//        try {
//            sendMessage("WebSocket连接成功！");
            log.info("有新窗口开始监听:" + sid + ",当前在线人数为:" + getOnlineCount());
//        } catch (IOException e) {
//            log.error("websocket IO Exception");
//        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从 set 中删除
        list.remove(sid);
        subOnlineCount(); // 在线数减 1
        log.info(" 有一连接关闭,窗口为：" + sid + "！当前在线人数为 " + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info(" 收到来自窗口 " + sid + " 的信息 :" + message);
        String [] strings1 = message.split(" ");
        log.info(strings1);
        String filePath = startPath;
        log.info("start path: "+startPath + "\t python path: " + pythonPath);
        String [] para = new String[] {pythonPath,
                filePath,
                strings1[0],
                strings1[1],
                strings1[2]};
//        String result = invokePy(para);
        String s = invokePy(para);
        log.info(s);
        String [] strings2 = s.split(" ");

        JSONObject res = new JSONObject();
        try {
            res.put("status1",strings2[2]);
            res.put("status2",strings2[3]);
            res.put("number",strings2[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String returnMessage = res.toString();

//        ProcessBuilder processBuilder = new ProcessBuilder("python",
//                resolvePythonScriptPath("weibo.py"));
//        processBuilder.redirectErrorStream(true);
//        List<String> results = null;
//        try {
//            log.info("start");
//            Process process = processBuilder.start();
//            results = readProcessOutput(process.getInputStream());
//        } catch (Exception e) {
//            log.error("python错误");
//        }

//        System.out.println("results = " + results);

        log.info(returnMessage);
        try {
            session.getBasicRemote().sendText(returnMessage);
        } catch (IOException e) {
            System.out.println("返回数据失败");
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error(" 发生错误 ");
        error.printStackTrace();
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    private String getFilesPath(String s) {
        String filePath = "";
        try {
            ClassPathResource classPathResource = new ClassPathResource(s);
            InputStream inputStream = classPathResource.getInputStream();
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            //生成临时文件,这个是生成在根目录下
            filePath = System.getProperty("user.dir") + "weibo.py";
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bdata);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" copy文件失败 ");
        }
        return filePath;
    }

    public String invokePy(String[] para) {
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(para);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                log.info(line);
                result.append(line).append(" ");
            }
            reader.close();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String resolvePythonScriptPath(String filename) {
        File file = new File(System.getProperty("user.dir") +
                "/publicopinion/src/main/java/com/tetesense/publicopinion/weibo/" + filename);
        return file.getAbsolutePath();
    }

    public static List<String> readProcessOutput(InputStream inputStream) throws IOException {
        try (BufferedReader output = new BufferedReader(new InputStreamReader(inputStream))) {
            return output.lines()
                    .collect(Collectors.toList());
        }
    }
}
