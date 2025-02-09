<template>
	<div>
		<div class="login-container">
			<el-form class="login_form animate__animated animate__backInDown">
				<div class="login_form2">
					<div class="title-container">基于SpringBoot的校园招聘推荐系统</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							账号 
						</div>
						<input placeholder="请输入账号 " name="username" type="text" v-model="rulesForm.username">
					</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							密码
						</div>
						<div class="password-box">
							<input placeholder="请输入密码" name="password" :type="showPassword?'text':'password'" v-model="rulesForm.password">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>

					<div class="list-item " v-if="roles.length>1">
						<div class="lable">
							角色
						</div>
						<div prop="loginInRole" class="list-type">
							<el-radio v-if="loginType==1||(loginType==2&&item.roleName!='管理员')" v-for="item in roles" v-bind:key="item.roleName" v-model="rulesForm.role" :label="item.roleName">{{item.roleName}}</el-radio>
						</div>
					</div>

		
					<div class="login-btn">
						<div class="login-btn1">
							<el-button v-if="loginType==1" type="primary" @click="login()" class="loginInBt">登录</el-button>
						</div>
						<div class="login-btn2">
							<el-button type="primary" @click="register('qiyexinxi')" class="register">
								注册企业信息							</el-button>
						</div>
						<div class="login-btn3">
						</div>
					</div>
				</div>
				<div class="idea-box1">登录</div>
				<div class="idea-box2"></div>
			</el-form>
		</div>
	</div>
</template>
<script>
	import 'animate.css'
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				verifyCheck2: false,
				flag: false,
				baseUrl:this.$base.url,
				loginType: 1,
				rulesForm: {
					username: "",
					password: "",
					role: "",
				},
				menus: [],
				roles: [],
				tableName: "",
				showPassword: false,
			};
		},
		mounted() {
			let menus = menu.list();
			this.menus = menus;

			for (let i = 0; i < this.menus.length; i++) {
				if (this.menus[i].hasBackLogin=='是') {
					this.roles.push(this.menus[i])
				}
			}

		},
		created() {

		},
		destroyed() {
		},
		components: {
		},
		methods: {

			//注册
			register(tableName){
				this.$storage.set("loginTable", tableName);
				this.$router.push({path:'/register',query:{pageFlag:'register'}})
			},
			// 登陆
			login() {

				if (!this.rulesForm.username) {
					this.$message.error("请输入用户名");
					return;
				}
				if (!this.rulesForm.password) {
					this.$message.error("请输入密码");
					return;
				}
				if(this.roles.length>1) {
					if (!this.rulesForm.role) {
						this.$message.error("请选择角色");
						return;
					}

					let menus = this.menus;
					for (let i = 0; i < menus.length; i++) {
						if (menus[i].roleName == this.rulesForm.role) {
							this.tableName = menus[i].tableName;
						}
					}
				} else {
					this.tableName = this.roles[0].tableName;
					this.rulesForm.role = this.roles[0].roleName;
				}
		
				this.loginPost()
			},
			loginPost() {
				this.$http({
					url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
					method: "post"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$storage.set("Token", data.token);
						this.$storage.set("role", this.rulesForm.role);
						this.$storage.set("sessionTable", this.tableName);
						this.$storage.set("adminName", this.rulesForm.username);
						this.$router.replace({ path: "/" });
					} else {
						this.$message.error(data.msg);
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
.login-container {
	min-height: 100vh;
	position: relative;
	background-repeat: no-repeat;
	background-position: center center;
	background-size: cover;
	background-repeat: no-repeat;
	background-size: 100% 100%;
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: center;
	background-image:  url(http://codegen.caihongy.cn/20240926/a12a8b82de814927adbd5783c98082da.jpg);
	background-position: center center;
	position: relative;

	.login_form {
		border-radius: 0;
		padding: 20px 69px 40px 25%;
		box-shadow: inset 0px 0px 0px 0px #000;
		margin: auto;
		z-index: 1000;
		background: url("http://codegen.caihongy.cn/20240926/ee2e080ca41941cf8b9064ea8d1df11d.png") left center /  48% 130% no-repeat, #fff;
		display: flex;
		width: 1200px;
		justify-content: flex-end;
		height: 670px;
		.login_form2 {
			flex-direction: column;
			background: #fff;
			display: flex;
			width: 75%;
		}
		.title-container {
			padding: 0 0px;
			margin: 30px 30px 30px -120px;
			color: #000000;
			background: none;
			font-weight: 500;
			width: calc(100% + 160px);
			font-size: 18px;
			font-family: Source Han Sans-Medium;
			line-height: 20px;
			text-align: center;
		}
		.list-item {
			padding: 0;
			margin: 10px 0 10px 10px;
			display: flex;
			width: calc(100% - 0px);
			position: relative;
			align-items: center;
			.lable {
				padding: 0 10px 0 0;
				color: #000;
				white-space: nowrap;
				letter-spacing: 1px;
				font-size: 16px;
				border-color: #000000;
				line-height: 40px;
				width: auto;
				border-width: 0 0 0px 0;
				border-style: solid;
				text-align: right;
				min-width: 80px;
				height: 40px;
			}
			input {
				padding: 0 10px;
				outline: none;
				color: #666;
				flex: 1;
				background: none;
				font-size: 16px;
				border-color: #000000;
				border-width: 0 0 2px 0;
				height: 40px;
			}
			input:focus {
				padding: 0 10px;
				color: #666;
				flex: 1;
				background: none;
				font-size: 16px;
				border-color: #000000;
				border-width: 0 0 2px 0;
				height: 40px;
			}
			.password-box {
				flex: 1;
				display: flex;
				position: relative;
				align-items: center;
				input {
					padding: 0 10px;
					color: #666;
					flex: 1;
					background: none;
					font-size: 16px;
					border-color: #000000;
					border-width: 0 0 2px 0;
					height: 40px;
				}
				input:focus {
					padding: 0 10px;
					color: #666;
					flex: 1;
					background: none;
					font-size: 16px;
					border-color: #000000;
					border-width: 0 0 2px 0;
					height: 40px;
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
				color: #999;
				font-size: 16px;
			}
		}
		.list-type {
			padding: 0;
			margin: 0;
			background: none;
			width: calc(100% - 0px);
			border-width: 0 0 2px 0;
			line-height: 40px;
			border-style: solid;
			height: 40px;
			/deep/ .el-radio__input .el-radio__inner {
				background: rgba(53, 53, 53, 0);
				border-color: #666;
			}
			/deep/ .el-radio__input.is-checked .el-radio__inner {
				background: #0d6efd;
				border-color: #0d6efd;
			}
			/deep/ .el-radio__label {
				color: #666;
				font-size: 16px;
			}
			/deep/ .el-radio__input.is-checked+.el-radio__label {
				color: #0d6efd;
				font-size: 16px;
			}
		}
		.login-btn {
			margin: 20px auto;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.login-btn1 {
				margin: 10px 0 10px 10px;
				display: flex;
				width: 100%;
				justify-content: center;
			}
			.login-btn2 {
				margin: 10px 0 10px 10px;
				display: flex;
				width: 100%;
				justify-content: center;
			}
			.login-btn3 {
				width: 100%;
			}
			.loginInBt {
				border: 0px solid rgba(0, 0, 0, 1);
				cursor: pointer;
				border-radius: 60px 60px 60px 60px;
				padding: 0 10px;
				margin: 0 0 10px;
				color: #fff;
				background: linear-gradient( 137deg, #57A5FF 0%, #90F4FC 100%);
				font-weight: 700;
				letter-spacing: 2px;
				font-size: 30px;
				min-width: 174px;
				height: 69px;
			}
			.loginInBt:hover {
				opacity: 0.8;
			}
			.register {
				border: 0px solid rgba(0, 0, 0, 1);
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0 0 10px;
				color: rgba(0, 0, 0, 1);
				background: #fff;
				width: auto;
				font-size: 16px;
				height: 34px;
			}
			.register:hover {
				opacity: 0.8;
			}
			.forget {
				border: 0;
				cursor: pointer;
				border-radius: 0;
				padding: 0;
				margin: 0 10px 10px 0;
				color: #000000;
				background: none;
				width: 100%;
				font-size: 15px;
				text-align: right;
				height: 34px;
			}
			.forget:hover {
				color: #0d6efd;
				opacity: 1;
			}
		}
	}
	.idea-box1 {
		background: none;
		font-weight: 600;
		display: none;
		width: 560px;
		font-size: 20px;
		height: 30px;
		order: -2;
	}
	.idea-box2 {
		margin: 5px 0 40px;
		background: #fff;
		display: none;
		width: 100%;
		font-size: 16px;
		height: 30px;
		order: -1;
	}
}

</style>
