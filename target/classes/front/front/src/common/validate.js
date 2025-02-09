export default {
	isEmail2: function(s) {
		return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
	},
	isEmail: function(rule, value, callback) {
		let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的邮箱'));
		} else {
			callback()
		}
	},
	isEmailNotNull: function(rule, value, callback) {
		let reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的邮箱'));
		} else if (!value) {
			callback(new Error('请输入邮箱'));
		} else {
			callback()
		}
	},
	isMobile2: function(s) {
		return /^1[3456789]\d{9}$/.test(s);
	},
	isMobile: function(rule, value, callback) {
		let reg = /^1[3456789]\d{9}$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的手机号码'));
		} else {
			callback()
		}
	},
	isMobileNotNull: function(rule, value, callback) {
		let reg = /^1[3456789]\d{9}$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的手机号码'));
		} else if (!value) {
			callback(new Error('请输入手机号码'));
		} else {
			callback()
		}
	},
	isPhone: function(rule, value, callback) {
		let reg = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的电话号码'));
		} else {
			callback()
		}
	},
	isPhone2: function(s) {
		return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
	},
	isPhoneNotNull: function(rule, value, callback) {
		let reg = /^([0-9]{3,4}-)?[0-9]{7,8}$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的电话号码'));
		} else if (!value) {
			callback(new Error('请输入电话号码'));
		} else {
			callback()
		}
	},
	isURL: function(rule, value, callback) {
		let reg = /^http[s]?:\/\/.*/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的URL地址'));
		} else {
			callback()
		}
	},
	isURL2: function(s) {
		return /^http[s]?:\/\/.*/.test(s)
	},
	isURLNotNull: function(rule, value, callback) {
		let reg = /^http[s]?:\/\/.*/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的URL地址'));
		} else if (!value) {
			callback(new Error('请输入地址'));
		} else {
			callback()
		}
	},
	isNumber: function(rule, value, callback) {
		let reg = /(^-?[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$)|(^$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的数字'));
		} else {
			callback()
		}
	},
	isNumber2: function(s) {
		return /(^-?[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$)|(^$)/.test(s);
	},
	isNumberNotNull: function(rule, value, callback) {
		let reg = /(^-?[+-]?([0-9]*\.?[0-9]+|[0-9]+\.?[0-9]*)([eE][+-]?[0-9]+)?$)|(^$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的数字'));
		} else if (!value) {
			callback(new Error('请输入数字'));
		} else {
			callback()
		}
	},
	isIntNumer: function(rule, value, callback) {
		let reg = /(^-?\d+$)|(^$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的整数'));
		} else {
			callback()
		}
	},
	isIntNumer2: function(s) {
		return /(^-?\d+$)|(^$)/.test(s);
	},
	isIntNumerNotNull: function(rule, value, callback) {
		let reg = /(^-?\d+$)|(^$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的整数'));
		} else if (!value) {
			callback(new Error('请输入整数'));
		} else {
			callback()
		}
	},
	isIdCard: function(rule, value, callback) {
		let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的身份证'));
		} else {
			callback()
		}
	},
	isIdCard2: function(idcard) {
		const regIdCard = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		if (!regIdCard.test(idcard)) {
			return false;
		} else {
			return true;
		}
	},
	isIdCardNotNull: function(rule, value, callback) {
		let reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入正确的身份证'));
		} else if (!value) {
			callback(new Error('请输入身份证'));
		} else {
			callback()
		}
	},
	isChinese: function(rule, value, callback) {
		let reg = /^[\u4e00-\u9fa5]+$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入中文'));
		} else {
			callback()
		}
	},
	isChinese2: function(value) {
		let reg = /^[\u4e00-\u9fa5]+$/;
		if (!reg.test(value)) {
			return false;
		} else {
			return true;
		}
	},
	isChineseNotNull: function(rule, value, callback) {
		let reg = /^[\u4e00-\u9fa5]+$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入中文'));
		} else if (!value) {
			callback(new Error('内容不能为空'));
		} else {
			callback()
		}
	},
	isEnglish: function(rule, value, callback) {
		let reg = /^[A-Za-z]+$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入英文'));
		} else {
			callback()
		}
	},
	isEnglish2: function(value) {
		let reg = /^[A-Za-z]+$/;
		if (!reg.test(value)) {
			return false;
		} else {
			return true;
		}
	},
	isEnglishNotNull: function(rule, value, callback) {
		let reg = /^[A-Za-z]+$/;
		if (value && reg.test(value) === false) {
			callback(new Error('请输入英文'));
		} else if (!value) {
			callback(new Error('内容不能为空'));
		} else {
			callback()
		}
	},
	isEnglishNumber: function(rule, value, callback) {
		let reg = /^[A-Za-z0-9]+$/;
		let reg2 = /^(?=.*\d)(?=.*[a-zA-Z]).+$/;
		if (value && reg.test(value) === false || reg2.test(value) === false) {
			callback(new Error('请输入英文和数字'));
		} else {
			callback()
		}
	},
	isEnglishNumber2: function(value) {
		let reg = /^[A-Za-z0-9]+$/;
		let reg2 = /^(?=.*\d)(?=.*[a-zA-Z]).+$/;
		if (!reg.test(value)||!reg2.test(value)) {
			return false;
		} else {
			return true;
		}
	},
	isEnglishNumberNotNull: function(rule, value, callback) {
		let reg = /^[A-Za-z0-9]+$/;
		let reg2 = /^(?=.*\d)(?=.*[a-zA-Z]).+$/;
		if (value && (reg.test(value) === false|| reg2.test(value) === false)) {
			callback(new Error('请输入英文和数字'));
		} else if (!value) {
			callback(new Error('内容不能为空'));
		} else {
			callback()
		}
	},
}