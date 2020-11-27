package iface.service;

import java.util.Optional;

import model.domain.Authentication;
import model.entity.MvcUser;

public interface MvcUsersService {

	MvcUser createMvcUser(String username, String password);

	Optional<MvcUser> readMvcUserByAuth(String auth);

	Optional<Authentication> readMvcUserByUsernameAndPassword(String username, String password);

}