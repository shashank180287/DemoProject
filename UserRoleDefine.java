
public enum UserRoleDefine {

	ADMIN(new AdminUserRoleHandler()),
	CUSTOMER(new CustomerUserRoleHandler()),
	EMPLOYEE(new EmployeeUserRoleHandler());
	
	private UserRoleHandler userRoleHandler;
	
	private UserRoleDefine(UserRoleHandler userRoleHandler) {
		this.userRoleHandler = userRoleHandler;
	}
	
	public UserRoleHandler getRoleHandler() {
		return this.userRoleHandler;
	}
}
