package com.bw.movie.model;

import java.util.List;

/**
 * @author 鲲
 * @time 12/02
 * 用户评论
 *
 */
public class MoviePeopleEvaluate {


	/**
	 * result : [{"replyContent":"ghh","replyHeadPic":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eowPGv3RnPhWq4iaeicUvTwyWO5U96L7LUnLqZSwhKYIE3jqDTOgmiaVibImricFoyyX8F8ymGb68lq3gw/132","replyTime":1542524906000,"replyUserId":962,"replyUserName":"Android_AHO"}]
	 * message : 查询成功
	 * status : 0000
	 */

	private String message;
	private String status;
	private List<ResultBean> result;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ResultBean> getResult() {
		return result;
	}

	public void setResult(List<ResultBean> result) {
		this.result = result;
	}

	public static class ResultBean {
		/**
		 * replyContent : ghh
		 * replyHeadPic : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eowPGv3RnPhWq4iaeicUvTwyWO5U96L7LUnLqZSwhKYIE3jqDTOgmiaVibImricFoyyX8F8ymGb68lq3gw/132
		 * replyTime : 1542524906000
		 * replyUserId : 962
		 * replyUserName : Android_AHO
		 */

		private String replyContent;
		private String replyHeadPic;
		private long replyTime;
		private int replyUserId;
		private String replyUserName;

		public String getReplyContent() {
			return replyContent;
		}

		public void setReplyContent(String replyContent) {
			this.replyContent = replyContent;
		}

		public String getReplyHeadPic() {
			return replyHeadPic;
		}

		public void setReplyHeadPic(String replyHeadPic) {
			this.replyHeadPic = replyHeadPic;
		}

		public long getReplyTime() {
			return replyTime;
		}

		public void setReplyTime(long replyTime) {
			this.replyTime = replyTime;
		}

		public int getReplyUserId() {
			return replyUserId;
		}

		public void setReplyUserId(int replyUserId) {
			this.replyUserId = replyUserId;
		}

		public String getReplyUserName() {
			return replyUserName;
		}

		public void setReplyUserName(String replyUserName) {
			this.replyUserName = replyUserName;
		}
	}
}
