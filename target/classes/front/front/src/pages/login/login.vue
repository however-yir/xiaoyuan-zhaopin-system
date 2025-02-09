<template>
	<div>
		<div id="particles"></div>
		<div class="login-container">
			<el-form ref="loginForm" :model="loginForm" :rules="rules" class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="login-title">基于SpringBoot的校园招聘推荐系统登录</div>
					<div v-if="loginType==1" class="list-item" prop="username">
						<div class="lable">
							账号：
						</div>
						<input v-model="loginForm.username" placeholder="请输入账号：">
					</div>
					<div v-if="loginType==1" class="list-item" prop="password">
						<div class="lable">
							密码：
						</div>
						<div class="password-box">
							<input v-model="loginForm.password" placeholder="请输入密码：" :type="showPassword?'text':'password'">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>

					<div class="list-item" v-if="roles.length>1">
						<div class="lable">
							
						</div>
						<div class="list-type" prop="role">
							<el-radio v-model="loginForm.tableName" :label="item.tableName" v-for="(item, index) in roles" :key="index" @change.native="getCurrentRow(item)">{{item.roleName}}</el-radio>
						</div>
					</div>

			
					<div class="list-btn">
						<el-button class="login_btn" v-if="loginType==1" @click="submitForm('loginForm')">登录</el-button>

						<div class="list-btn2">
							<router-link class="register_btn" :to="{path: '/register', query: {role: item.tableName,pageFlag:'register'}}" v-if="item.hasFrontRegister=='是'" v-for="(item, index) in roles" :key="index">注册{{item.roleName.replace('注册','')}}</router-link>
						</div>
					</div>
				</div>
				<div class="idea1"></div>
				<div class="idea2"></div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css';
import menu from '@/config/menu'
	require('@/common/particles.js')
export default {
	//数据集合
	data() {
		return {
            baseUrl: this.$config.baseUrl,
            loginType: 1,
			roleMenus: [],
			loginForm: {
				username: '',
				password: '',
				tableName: '',
				code: '',
			},
			role: '',
            roles: [],
			rules: {
				username: [
					{ required: true, message: '请输入账号', trigger: 'blur' }
				],
				password: [
					{ required: true, message: '请输入密码', trigger: 'blur' }
				]
			},
			codes: [{
				num: 1,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 2,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 3,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}, {
				num: 4,
				color: '#000',
				rotate: '10deg',
				size: '16px'
			}],
			flag: false,
			verifyCheck2: false,
			showPassword: false,
			particlesJson: {"retina_detect":true,"particles":{"number":{"density":{"value_area":800,"enable":true},"value":160},"move":{"straight":false,"random":true,"bounce":false,"enable":true,"attract":{"rotateX":600,"enable":false,"rotateY":600},"speed":1,"direction":"none","out_mode":"out"},"color":{"value":"#0AB8C1"},"shape":{"image":{"width":100,"src":"img/github.svg","height":100},"polygon":{"nb_sides":5},"type":"circle","stroke":{"color":"#000000","width":0}},"size":{"anim":{"size_min":0.3,"sync":false,"enable":false,"speed":4},"random":true,"value":3},"line_linked":{"width":1,"distance":150,"color":"#ffffff","opacity":0.4,"enable":false},"opacity":{"anim":{"opacity_min":0,"sync":false,"enable":true,"speed":1},"random":true,"value":1}},"interactivity":{"detect_on":"canvas","events":{"resize":true,"onclick":{"mode":"repulse","enable":true},"onhover":{"mode":"bubble","enable":true}},"modes":{"grab":{"distance":400,"line_linked":{"opacity":1}},"bubble":{"duration":2,"distance":250,"size":0,"opacity":0,"speed":3},"repulse":{"duration":0.4,"distance":400},"push":{"particles_nb":4},"remove":{"particles_nb":2}}}}
		}
	},
	components: {
	},
	created() {
		this.roleMenus = menu.list()
		for(let item in this.roleMenus) {
			if(this.roleMenus[item].hasFrontLogin=='是') {
				this.roles.push(this.roleMenus[item]);
			}
		}
		
	},
	mounted() {
		particlesJS('particles',this.particlesJson);
	},
	//方法集合
	methods: {
		randomString() {
			var len = 4;
			var chars = [
				'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
				'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G',
				'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2',
				'3', '4', '5', '6', '7', '8', '9'
			]
			var colors = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f']
			var sizes = ['14', '15', '16', '17', '18']
			
			var output = []
			for (var i = 0; i < len; i++) {
				// 随机验证码
				var key = Math.floor(Math.random() * chars.length)
				this.codes[i].num = chars[key]
				// 随机验证码颜色
				var code = '#'
				for (var j = 0; j < 6; j++) {
					var key = Math.floor(Math.random() * colors.length)
					code += colors[key]
				}
				this.codes[i].color = code
				// 随机验证码方向
				var rotate = Math.floor(Math.random() * 45)
				var plus = Math.floor(Math.random() * 2)
				if (plus == 1) rotate = '-' + rotate
				this.codes[i].rotate = 'rotate(' + rotate + 'deg)'
				// 随机验证码字体大小
				var size = Math.floor(Math.random() * sizes.length)
				this.codes[i].size = sizes[size] + 'px'
			}
		},
		getCurrentRow(row) {
			this.role = row.roleName;
		},
		submitForm(formName) {
			if (this.roles.length!=1) {
				if (!this.role) {
					this.$message.error("请选择登录用户类型");
					return false;
				}
			} else {
				this.role = this.roles[0].roleName;
				this.loginForm.tableName = this.roles[0].tableName;
			}
			if (!this.loginForm.username) {
				this.$message.error("请输入用户名");
				return;
			}
			if (!this.loginForm.password) {
				this.$message.error("请输入密码");
				return;
			}

			this.loginPost(formName)
		},
		loginPost(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.$http.get(`${this.loginForm.tableName}/login`, {params: this.loginForm}).then(res => {
						if (res.data.code === 0) {
							localStorage.setItem('frontToken', res.data.token);
							localStorage.setItem('UserTableName', this.loginForm.tableName);
							localStorage.setItem('username', this.loginForm.username);
							// localStorage.setItem('adminName', this.loginForm.username);
							localStorage.setItem('frontSessionTable', this.loginForm.tableName);
							localStorage.setItem('frontRole', this.role);
							localStorage.setItem('keyPath', 0);
							this.$router.push('/');
							this.$message({
								message: '登录成功',
								type: 'success',
								duration: 1500,
							});
						} else {
							this.$message.error(res.data.msg);
						}
					});
				} else {
					return false;
				}
			});
		},
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.login-container {
		background-repeat: no-repeat;
		background-size: 100% 100%;
		background: url(http://codegen.caihongy.cn/20241031/a4f3a13e88a24a32af35565eff05efd0.png);
		display: flex;
		width: 100%;
		min-height: 100vh;
		justify-content: center;
		align-items: center;
		background-position: center center;
		position: relative;
		background: url(http://codegen.caihongy.cn/20241031/a4f3a13e88a24a32af35565eff05efd0.png);
		.login_form {
			border-radius: 20px;
			padding: 70px 20px 20px;
			box-shadow: 0 0px 0px rgba(64, 158, 255, .8);
			margin: 0;
			z-index: 2;
			background: url(http://codegen.caihongy.cn/20241031/382a552f717a4520817264ea42222b43.png) top center/100% auto no-repeat #fff;
			width: 40%;
			height: auto;
			.login_form2 {
				width: 100%;
				.login-title {
					margin: 0 0 120px;
					color: #fff;
					font-weight: bold;
					width: 100%;
					font-size: 30px;
					line-height: 1;
					text-align: center;
				}
				.list-item {
					margin: 0 auto 30px;
					display: flex;
					width: 80%;
					.lable {
						color: #000;
						width: 90px;
						font-size: 14px;
						line-height: 44px;
					}
					input {
						border: 1px solid #D8D8D8;
						padding: 0 10px;
						box-shadow: 0 0 0px rgba(64, 158, 255, .5);
						outline: 0px solid #efefef;
						color: #000;
						width: calc(100% - 90px);
						font-size: 14px;
						border-width: 0 0 2px;
						height: 44px;
					}
					input:focus {
						border: 1px solid #0AB8C1;
						padding: 0 10px;
						box-shadow: 0 0 0px rgba(64, 158, 255, .5);
						outline: 0px solid #efefef;
						color: #000;
						width: calc(100% - 90px);
						font-size: 14px;
						border-width: 0 0 2px;
						height: 44px;
					}
					.password-box {
						display: flex;
						width: calc(100% - 90px);
						position: relative;
						align-items: center;
						input {
							border: 1px solid #D8D8D8;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: 0px solid #efefef;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 2px;
							height: 44px;
						}
						input:focus {
							border: 1px solid #0AB8C1;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: 0px solid #efefef;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 2px;
							height: 44px;
						}
						.iconfont {
							cursor: pointer;
							z-index: 1;
							color: #000;
							top: 0;
							font-size: 16px;
							line-height: 44px;
							position: absolute;
							right: 5px;
						}
					}
					input::placeholder {
						color: #123;
						font-size: 15px;
					}
				}
				.list-type {
					margin: 20px auto;
					width: 80%;
					/deep/ .el-radio__input .el-radio__inner {
						background: rgba(53, 53, 53, 0);
						border-color: #9E9E9E;
						border-width: 4px;
					}
					/deep/ .el-radio__input.is-checked .el-radio__inner {
						background: #0AB8C1;
						border-color: #0AB8C1;
					}
					/deep/ .el-radio__label {
						color: #9E9E9E;
						font-size: 14px;
					}
					/deep/ .el-radio__input.is-checked+.el-radio__label {
						color: #0AB8C1;
						font-size: 14px;
					}
				}
				.list-btn {
					margin: 20px auto;
					width: 80%;
					.login_btn {
						border: 0;
						cursor: pointer;
						border-radius: 5px;
						padding: 0 24px;
						margin: 0 5px;
						outline: none;
						color: #fff;
						background: #0AB8C1;
						width: 100%;
						font-size: 18px;
						height: 50px;
					}
					.login_btn:hover {
						opacity: 0.5;
					}
					.list-btn2 {
						margin: 20px auto;
						display: flex;
						width: 100%;
						flex-wrap: wrap;
						.register_btn {
							cursor: pointer;
							border: 1px solid #0AB8C1;
							border-radius: 5px;
							margin: 0 auto 10px;
							color: #0AB8C1;
							background: #fff;
							text-decoration: none;
							width: 30%;
							font-size: 18px;
							line-height: 50px;
							text-align: center;
						}
						.register_btn:hover {
							opacity: 0.5;
						}
						.resetpwd_btn {
							cursor: pointer;
							border-radius: 5px;
							margin: 0 auto;
							color: #9E9E9E;
							background: none;
							text-decoration: none;
							width: 100%;
							font-size: 14px;
							line-height: 50px;
							text-align: right;
						}
						.resetpwd_btn:hover {
							opacity: 0.5;
						}
					}
				}
			}
			.idea1 {
				background: red;
				display: none;
				width: 100%;
				height: 40px;
			}
			.idea2 {
				background: blue;
				display: none;
				width: 100%;
				height: 40px;
			}
		}
	}
	#particles{
		background-repeat: no-repeat;
		z-index: 1;
		background-size: cover;
		width: 100%;
		position: absolute;
		background-position: 50% 50%;
		height: 100%;
	}
</style>
