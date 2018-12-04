package com.bw.movie.net;

/**
 * 魏天祥
 * 2018/11/27
 */
public class HttpUrl {
    //注册
    public static final String STRING_REG = "user/v1/registerUser";
    //登录
    public static final String STRING_LOGIN = "user/v1/login";
    //查询用户首页信息
    public static final String STRING_SELECT = "";
    //修改用户信息
    public static final String STRING_UPDATA_USER = "";
    //上传用户头像
    public static final String STRING_BTN_HEAD = "";
    //修改密码
    public static final String STRING_UPDATA = "";
    //根据用户ID查询用户信息
    public static final String STRING_ID_SELECT = "";
    //用户签到
    public static final String STRING_SIGN_IN = "";
    //用户购票记录查询列表
    public static final String STRING_RECORD_SELECT = "user/v1/verify/findUserBuyTicketRecordList";
    //微信登录
    public static final String STRING_WECHAT_LOGIN = "";
    //绑定微信账号
    public static final String STRING_BINDING_WECHAT = "";
    //是否绑定微信账号
    //查询热门电影列表

    //查询正在热映电影列表

    //查询正在上映电影列表
    public static final String STRING_HOT_MOVIE = "movie/v1/findHotMovieList";
    //查询正在热映电影列表
    public static final String STRING_SHOW_MOVIE = "movie/v1/findReleaseMovieList";
    //查询正在上映电影列表
    public static final String STRING_WILL_MOVIE = "movie/v1/findComingSoonMovieList";
    //根据电影ID查询电影信息

    //查看电影详情
    public static final String STRING_DETAIL_MOVIE = "movie/v1/findMoviesDetail";
    //查询用户关注的影片列表
    public static final String STRING_ATTENTION_MOVIES = "movie/v1/verify/findMoviePageList";

    //关注电影
    public static final String STRING_ATTENTION_MOVIE = "movie/v1/verify/followMovie";
    //取消关注电影
    public static final String STRING_CANCLE_ATTENTION_MOVIE="movie/v1/verify/cancelFollowMovie";

    //查询影片评论列表
    public static final String STRING_EVALUATE_MOVIE="movie/v1/findAllMovieComment";
    //添加用户对影片的评论
    public static final String STRING_ADD_EVALUATE_MOVIE="movie/v1/verify/movieComment";

    //查询影片评论回复
    public static final String STRING_PEOPLE_EVALUATE_MOVIE="movie/v1/findCommentReply";
    //添加用户对评论的回复
    public static final String STRING_ADD_PEOPLE_EVALUATE_MOVIE="movie/v1/verify/commentReply";
    //电影评论点赞
    public static final String STRING_GREAT_MOVIE="movie/v1/verify/movieCommentGreat";
    //根据影院ID查询该影院当前排期的电影列表
    public static final String MOVIE_BY_CINEMA_ID = "movie/v1/findMovieListByCinemaId";
    //根据电影ID和影院ID查询电影排期列表
    public static final String CINEMA_MOVIE_INFO = "movie/v1/findMovieScheduleList";
    //根据电影ID查询当前排片该电影的影院列表
    //购票下单
    //支付【支持微信和支付宝支付】

    //查询推荐影院信息
    public static final String RECOMMEND_CINEMA = "cinema/v1/findRecommendCinemas";
    //查询电影信息明细
    public static final String CINEMA_INFO = "cinema/v1/findCinemaInfo";
    //查询所有电影院
    public static final String CINEMA_SEARCH_ALL = "cinema/v1/findAllCinemas";
    //查询用户关注的影院信息
    public static final String STRING_ATTENTION_CINEMA = "cinema/v1/verify/findCinemaPageList";


    //关注影院
    //取消关注影院
    //查询影院用户评论列表
    public static final String CINEMA_COMMENT_ALL = "cinema/v1/findAllCinemaComment";
    //影院评论
    //影院评论点赞

    //意见反馈
    public static final String APP_FEED_BACK = "tool/v1/verify/recordFeedBack";
    //查询新版本
    public static final String APP_VERSION_CODE = "tool/v1/findNewVersion";
    //查询系统消息列表
    public static final String SYSM_SGLIST = "tool/v1/verify/findAllSysMsgList";
    //系统消息读取状态修改
    //查询用户当前未读消息数量
    //上传信鸽推送token
    //微信分享

}
