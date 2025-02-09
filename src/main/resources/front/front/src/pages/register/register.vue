<template>
	<div>
		<div id="particles"></div>

		<div class="container">
			<el-form class='rgs-form animate__animated animate__' v-if="pageFlag=='register'" ref="registerForm" :model="registerForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">基于SpringBoot的校园招聘推荐系统注册</p></div>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="yonghuzhanghao">
						<div class="label" :class="changeRules('yonghuzhanghao')?'required':''">用户账号：</div>
						<el-input v-model="registerForm.yonghuzhanghao"  placeholder="请输入用户账号" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="mima">
						<div class="label" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input v-model="registerForm.mima" type="password" placeholder="请输入密码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="mima2">
						<div class="label" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input v-model="registerForm.mima2" type="password" placeholder="请再次输入密码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="yonghuxingming">
						<div class="label" :class="changeRules('yonghuxingming')?'required':''">用户姓名：</div>
						<el-input v-model="registerForm.yonghuxingming"  placeholder="请输入用户姓名" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="xingbie">
						<div class="label" :class="changeRules('xingbie')?'required':''">性别：</div>
						<el-select v-model="registerForm.xingbie" placeholder="请选择性别" >
							<el-option
								v-for="(item,index) in yonghuxingbieOptions"
								:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="touxiang">
						<div class="label" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="1"
							:multiple="true"
							:fileUrls="registerForm.touxiang?registerForm.touxiang:''"
							@change="yonghutouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'" prop="lianxifangshi">
						<div class="label" :class="changeRules('lianxifangshi')?'required':''">联系方式：</div>
						<el-input v-model="registerForm.lianxifangshi"  placeholder="请输入联系方式" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="qiyezhanghao">
						<div class="label" :class="changeRules('qiyezhanghao')?'required':''">企业账号：</div>
						<el-input v-model="registerForm.qiyezhanghao"  placeholder="请输入企业账号" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="qiyemingcheng">
						<div class="label" :class="changeRules('qiyemingcheng')?'required':''">企业名称：</div>
						<el-input v-model="registerForm.qiyemingcheng"  placeholder="请输入企业名称" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="mima">
						<div class="label" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input v-model="registerForm.mima" type="password" placeholder="请输入密码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="mima2">
						<div class="label" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input v-model="registerForm.mima2" type="password" placeholder="请再次输入密码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="fengmian">
						<div class="label" :class="changeRules('fengmian')?'required':''">封面：</div>
						<file-upload
							tip="点击上传封面"
							action="file/upload"
							:limit="1"
							:multiple="true"
							:fileUrls="registerForm.fengmian?registerForm.fengmian:''"
							@change="qiyexinxifengmianUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="farendaibiao">
						<div class="label" :class="changeRules('farendaibiao')?'required':''">法人代表：</div>
						<el-input v-model="registerForm.farendaibiao"  placeholder="请输入法人代表" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="lianxifangshi">
						<div class="label" :class="changeRules('lianxifangshi')?'required':''">联系方式：</div>
						<el-input v-model="registerForm.lianxifangshi"  placeholder="请输入联系方式" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='qiyexinxi'" prop="qiyedizhi">
						<div class="label" :class="changeRules('qiyedizhi')?'required':''">企业地址：</div>
						<el-input v-model="registerForm.qiyedizhi"  placeholder="请输入企业地址" />
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<el-button class="register_btn" type="primary" @click="submitForm('registerForm')">注册</el-button>
						</div>
						<div class="register-btn2">
							<router-link class="has_btn" to="/login">已有账号，直接登录</router-link>
						</div>
					</div>
				</div>
				<div class="idea1"></div>
				<div class="idea2"></div>
			</el-form>
		</div>
	</div>
</div>
</template>

<script>
	import 'animate.css';
	require('@/common/particles.js')

export default {
    //数据集合
    data() {
		return {
            pageFlag : '',
			tableName: '',
			registerForm: {},
			forgetForm: {},
			rules: {},
			requiredRules: {},
            yonghuxingbieOptions: [],
			particlesJson: {"retina_detect":true,"particles":{"number":{"density":{"value_area":800,"enable":true},"value":160},"move":{"straight":false,"random":true,"bounce":false,"enable":true,"attract":{"rotateX":600,"enable":false,"rotateY":600},"speed":1,"direction":"none","out_mode":"out"},"color":{"value":"#0AB8C1"},"shape":{"image":{"width":100,"src":"img/github.svg","height":100},"polygon":{"nb_sides":5},"type":"circle","stroke":{"color":"#000000","width":0}},"size":{"anim":{"size_min":0.3,"sync":false,"enable":false,"speed":4},"random":true,"value":3},"line_linked":{"width":1,"distance":150,"color":"#ffffff","opacity":0.4,"enable":false},"opacity":{"anim":{"opacity_min":0,"sync":false,"enable":true,"speed":1},"random":true,"value":1}},"interactivity":{"detect_on":"canvas","events":{"resize":true,"onclick":{"mode":"repulse","enable":true},"onhover":{"mode":"bubble","enable":true}},"modes":{"grab":{"distance":400,"line_linked":{"opacity":1}},"bubble":{"duration":2,"distance":250,"size":0,"opacity":0,"speed":3},"repulse":{"duration":0.4,"distance":400},"push":{"particles_nb":4},"remove":{"particles_nb":2}}}}
		}
    },
	mounted() {
		if(this.$route.query.pageFlag=='register'){
			this.tableName = this.$route.query.role;
			if(this.tableName=='yonghu'){
				this.registerForm = {
					yonghuzhanghao: '',
					mima: '',
					mima2: '',
					yonghuxingming: '',
					xingbie: '',
					touxiang: '',
					lianxifangshi: '',
					sfsh: '',
					shhf: '',
				}
			}
			if(this.tableName=='qiyexinxi'){
				this.registerForm = {
					qiyezhanghao: '',
					qiyemingcheng: '',
					mima: '',
					mima2: '',
					fengmian: '',
					farendaibiao: '',
					lianxifangshi: '',
					qiyedizhi: '',
					qiyejianjie: '',
					sfsh: '',
					shhf: '',
				}
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }];
				this.requiredRules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }];
				this.requiredRules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }];
				this.requiredRules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }]
			}
			this.yonghuxingbieOptions = "男,女".split(',');
			if ('yonghu' == this.tableName) {
				this.rules.lianxifangshi = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
			}
			if ('qiyexinxi' == this.tableName) {
				this.rules.qiyezhanghao = [{ required: true, message: '请输入企业账号', trigger: 'blur' }];
				this.requiredRules.qiyezhanghao = [{ required: true, message: '请输入企业账号', trigger: 'blur' }]
			}
			if ('qiyexinxi' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }];
				this.requiredRules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('qiyexinxi' == this.tableName) {
				this.rules.lianxifangshi = [{ required: true, validator: this.$validate.isMobile, trigger: 'blur' }];
			}
		}
		particlesJS('particles',this.particlesJson);
	},
    created() {
		this.pageFlag = this.$route.query.pageFlag;
    },
    //方法集合
    methods: {
		changeRules(name){
			if(this.requiredRules[name]){
				return true
			}
			return false
		},
		// 获取uuid
		getUUID () {
			return new Date().getTime();
		},
        // 下二随
		yonghutouxiangUploadChange(fileUrls) {
			this.registerForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
		},
		qiyexinxifengmianUploadChange(fileUrls) {
			this.registerForm.fengmian = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
		},

		// 多级联动参数


		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					var url=this.tableName+"/register";
					if(this.tableName=='yonghu'){
						this.registerForm.sfsh = '待审核'
					}
					if(`yonghu` == this.tableName && this.registerForm.mima!=this.registerForm.mima2) {
						this.$message.error(`两次密码输入不一致`);
						return
					}
					if(this.tableName=='qiyexinxi'){
						this.registerForm.sfsh = '待审核'
					}
					if(`qiyexinxi` == this.tableName && this.registerForm.mima!=this.registerForm.mima2) {
						this.$message.error(`两次密码输入不一致`);
						return
					}
					this.$http.post(url, this.registerForm).then(res => {
						if (res.data.code === 0) {
							this.$message({
								message: '注册成功',
								type: 'success',
								duration: 1500,
								onClose: () => {
									this.$router.push('/login');
								}
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
		resetForm(formName) {
			this.$refs[formName].resetFields();
		}
    }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.container {
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
		.rgs-form {
			border-radius: 20px;
			padding: 70px 20px 20px;
			box-shadow: 0 0px 0px rgba(64, 158, 255, .8);
			margin: 0;
			z-index: 2;
			background: url(http://codegen.caihongy.cn/20241031/382a552f717a4520817264ea42222b43.png) top center/100% auto no-repeat #fff;
			width: 40%;
			height: auto;
			.rgs-form2 {
				width: 100%;
				.title {
					margin: 0 0 120px;
					color: #fff;
					font-weight: bold;
					width: 100%;
					font-size: 30px;
					line-height: 1;
					text-align: center;
				}
				.subtitle {
					margin: 0 0 10px 0;
					text-shadow: 4px 4px 2px rgba(64, 158, 255, .5);
					color: rgba(64, 158, 255, 1);
					width: 100%;
					font-size: 20px;
					line-height: 44px;
					text-align: center;
				}
				.list-item {
					margin: 0 auto 15px;
					display: flex;
					width: 80%;
					/deep/.el-form-item__content {
						display: flex;
						width: 100%;
						align-items: flex-start;
						.label {
							color: #000;
							width: 80px;
							font-size: 13px;
							line-height: 44px;
						}
						
						.required {
							position: relative;
						}
						.required::after{
							color: red;
							left: -10px;
							position: absolute;
							content: "*";
						}
						.el-input {
							width: calc(100% - 80px);
						}
						.el-input .el-input__inner {
							border: 1px solid #D6D6D6;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-input .el-input__inner:focus {
							border: 1px solid #0AB8C1;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-input-number {
							width: calc(100% - 80px);
						}
						.el-input-number .el-input__inner {
							text-align: left;
							border: 1px solid #D6D6D6;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-input-number .el-input-number__decrease {
							display: none;
						}
						.el-input-number .el-input-number__increase {
							display: none;
						}
						.el-select {
							width: calc(100% - 80px);
						}
						.el-select .el-input__inner {
							border: 1px solid #D6D6D6;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-select .el-input__inner:focus {
							border: 1px solid #0AB8C1;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-date-editor {
							width: calc(100% - 80px);
						}
						.el-date-editor .el-input__inner {
							border: 1px solid #D6D6D6;
							border-radius: 0;
							padding: 0 10px 0 30px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-date-editor .el-input__inner:focus {
							border: 1px solid #0AB8C1;
							border-radius: 0;
							padding: 0 10px 0 30px;
							box-shadow: 0 0 0px rgba(85, 170, 0, 0.5);
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-upload--picture-card {
							background: transparent;
							border: 0;
							border-radius: 0;
							width: auto;
							height: auto;
							line-height: initial;
							vertical-align: middle;
						}
						.upload .upload-img {
							border: 1px solid #D6D6D6;
							cursor: pointer;
							border-radius: 0;
							color: #D6D6D6;
							width: 100px;
							font-size: 32px;
							line-height: 100px;
							text-align: center;
							height: 100px;
						}
						.el-upload-list .el-upload-list__item {
							border: 1px solid #D6D6D6;
							cursor: pointer;
							border-radius: 0;
							color: #D6D6D6;
							width: 100px;
							font-size: 32px;
							line-height: 100px;
							text-align: center;
							height: 100px;
							font-size: 14px;
							line-height: 1.8;
						}
						.el-upload .el-icon-plus {
							border: 1px solid #D6D6D6;
							cursor: pointer;
							border-radius: 0;
							color: #D6D6D6;
							width: 100px;
							font-size: 32px;
							line-height: 100px;
							text-align: center;
							height: 100px;
						}
						.el-upload__tip {
							color: #838fa1;
							display: none;
						}
						.emailInput {
							border: 1px solid #D6D6D6;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.emailInput:focus {
							border: 1px solid #0AB8C1;
							border-radius: 0;
							padding: 0 10px;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							color: #000;
							width: 100%;
							font-size: 14px;
							border-width: 0 0 1px;
							height: 44px;
						}
						.el-btn {
							border: 0;
							cursor: pointer;
							padding: 0;
							margin: 0;
							color: #fff;
							font-size: 12px;
							line-height: 44px;
							border-radius: 0;
							box-shadow: 0 0 0px rgba(64, 158, 255, .5);
							outline: none;
							background: #0AB8C1;
							width: 100px;
							height: 44px;
						}
						.el-btn:hover {
							opacity: 0.5;
						}
						
						.el-input__inner::placeholder {
							color: #123;
							font-size: 16px;
						}
						input::placeholder {
							color: #123;
							font-size: 16px;
						}
						.editor {
							width: calc(100% - 80px);
							height: auto;
						}
					}
				}
				.register-btn {
					margin: 0 auto;
					width: 80%;
				}
				.register-btn1 {
					padding: 0 0 0 80px;
					width: 100%;
				}
				.register-btn2 {
					width: 100%;
					text-align: center;
				}
				.register_btn {
					border: 0;
					cursor: pointer;
					padding: 0 10px;
					margin: 20px auto 5px;
					color: #fff;
					display: block;
					font-size: 16px;
					border-radius: 0;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					background: #0AB8C1;
					width: 100%;
					height: 44px;
				}
				.register_btn:hover {
					opacity: 0.5;
				}
				.has_btn {
					cursor: pointer;
					padding: 0 10%;
					color: #9E9E9E;
					display: inline-block;
					text-decoration: none;
					font-size: 12px;
					line-height: 1;
				}
				.has_btn:hover {
					opacity: 0.5;
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
	::-webkit-scrollbar {
		display: none;
	}
</style>
