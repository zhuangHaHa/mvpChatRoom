package constants;

public interface chatAboutConstants {

    public static class userObj{
        public static final int USER_LOGIN_SUCCESS = 1000;
        public static final int USER_LOGIN_FAILED = 1001;
        public static final int PASSWORD_ERROR = 1002;
        public static final int USERNAME_ERROR = 1003;
        public static final int RULE_OUT = 1004;
        public static final int EMPTY_USERNAME_PASSWORD = 1005;

    }

    public static class chatMsgObj{
        public static final int ENTERROOM_SUCCESS = 2000;
        public static final int ENTERROOM_FAIL = 2001;
        public static final int CHATMSG_SEND_SUCCESS =2003;
        public static final int CHATMSG_SEND_FAILED = 2004;
        public static final int CHATMSG_RECEIVE_SUCCESS = 2005;
    }
}
