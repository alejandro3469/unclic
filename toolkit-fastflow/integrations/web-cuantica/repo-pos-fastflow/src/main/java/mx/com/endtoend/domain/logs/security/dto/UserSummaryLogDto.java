package mx.com.endtoend.domain.logs.security.dto;

import java.util.Date;

import mx.com.endtoend.domain.users.dto.UserDto;

public class UserSummaryLogDto {

	private String user;

	private Date updatedDate;

	private Long userId;

	private String username;

	private UserDto userSaved;

	private UserDto userUpdated;

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public UserDto getUserSaved() {
		return userSaved;
	}

	public void setUserSaved(UserDto userSaved) {
		this.userSaved = userSaved;
	}

	public UserDto getUserUpdated() {
		return userUpdated;
	}

	public void setUserUpdated(UserDto userUpdated) {
		this.userUpdated = userUpdated;
	}

	@Override
	public String toString() {
		return "UserSummaryLogDto [user=" + user + ", updatedDate=" + updatedDate + ", userId=" + userId + ", username="
				+ username + ", userSaved=" + userSaved + ", userUpdated=" + userUpdated + "]";
	}

}
