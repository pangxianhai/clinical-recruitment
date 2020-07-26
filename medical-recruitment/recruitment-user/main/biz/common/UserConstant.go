package common

type userConstant struct {
	UserMale     byte
	UserFemale   byte
	UserUnknown  byte
	StatusNormal byte
	StatusFreeze byte
	RoleManager  byte
	RoleCustomer byte
}

var UserConstant = userConstant{
	UserMale:    1,
	UserFemale:  2,
	UserUnknown: 3,

	StatusNormal: 1,
	StatusFreeze: 2,

	RoleManager:  1,
	RoleCustomer: 2,
}
