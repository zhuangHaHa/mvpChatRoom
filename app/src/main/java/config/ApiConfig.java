package config;

public class ApiConfig {

    private static String url = "192.168.86.1:6263";//测试url
//    private static String url = "20272l99v2.wicp.vip";//花生壳

    public static final String BASE_URL = "http://"+url+"/wechat/api/";

    public static final String channelURL = "ws://"+url+"/userListChannel";//mainFace用来获得实时用户信息

    public static final String chatChannelUrl = "ws://"+url+"/chatConsumer";
}
