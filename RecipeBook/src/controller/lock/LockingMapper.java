package controller.lock;

import java.sql.SQLException;

import javax.servlet.http.HttpSession;

import config.session.AppSession;
import config.session.Session;
import datasource.datamapper.CommentDataMapper;
import model.Comment;
import model.User;

public class LockingMapper {
	private CommentDataMapper cdm;
	private String session_userName;
	private User user;
	
	public LockingMapper(CommentDataMapper cdm, User user) {
		this.cdm = cdm;
		this.user = user;
		this.session_userName = user.getUserName();
	}
	
	public Comment read(int commentId) throws SQLException {
		ExclusiveReadLockManager erlm = new ExclusiveReadLockManager();
		
		int result = erlm.acquireLock(commentId, getSession_userName());
		
		if(result == -1) {
			Comment comment = new Comment(-1, -1, "NOT AVAILABLE, Under use", "NOT AVAILABLE, Under use");
			return comment;
		}
		else {
			Comment comment = new Comment(commentId);
			return getCdm().read(comment);
		}
	}

	public CommentDataMapper getCdm() {
		return cdm;
	}

	public void setCdm(CommentDataMapper cdm) {
		this.cdm = cdm;
	}

	public String getSession_userName() {
		return session_userName;
	}

	public void setSession_userName(String session_userName) {
		this.session_userName = session_userName;
	}
}
