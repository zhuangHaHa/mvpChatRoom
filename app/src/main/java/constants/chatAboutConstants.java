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

    public static class friendApply{
        public static final int GETSELECTEDUSER_SUCCESS = 3000;
        public static final int GETSELECTEDUSER_FAILED = 3001;
        public static final int ADDFRIEND_SUCCESS = 3003;
        public static final int ADDFRIEND_FAILED = 3004;
        public static final int DELETFRIEND_SUCCESS = 3005;
        public static final int DELETFRIEND_FAILED = 3006;
        public static final int APPLYSEND_SUCCESS = 3007;
        public static final int APPLYSEND_FAILED = 3008;
        public static final int APPLYSEND_EXIST = 3009;

    }

    public static class chatMsgObj{
        public static final int ENTERROOM_SUCCESS = 2000;
        public static final int ENTERROOM_FAIL = 2001;
        public static final int CHATMSG_SEND_SUCCESS =2003;
        public static final int CHATMSG_SEND_FAILED = 2004;
        public static final int CHATMSG_RECEIVE_SUCCESS = 2005;
        public static final int LEAVEROOM_SUCCESS = 2006;
        public static final int CHAT_WITHOTHERS = 2007;
        public static final int GETCHATMSGDATA_SUCCESS = 2008;
        public static final int GETCHATMSGDATA_FAILED = 2009;
    }
}
