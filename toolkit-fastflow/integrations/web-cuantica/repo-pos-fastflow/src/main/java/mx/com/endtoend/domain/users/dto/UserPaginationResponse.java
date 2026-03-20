package mx.com.endtoend.domain.users.dto;

import java.util.List;

public class UserPaginationResponse {

	List<UserDto> userList;

	int totalPage;

	public List<UserDto> getUserList() {
		return userList;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setUserList(List<UserDto> userList) {
		this.userList = userList;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	@Override
	public String toString() {
		return "UserPaginationResponse [userList=" + userList + ", totalPage=" + totalPage + "]";
	}

}
