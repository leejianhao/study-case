package dianshang.greencloud.user.service;

import dianshang.greencloud.user.model.User;

public interface IUserService {
	public void add(User user);
	public User loadByUsername(String username);
}
