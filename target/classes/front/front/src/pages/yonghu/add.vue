<template>
	<div class="add-update-preview">
		<el-form
			class="add-update-form"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="150px"
			>
			<el-form-item class="add-item" label="用户账号" prop="yonghuzhanghao">
				<el-input v-model="ruleForm.yonghuzhanghao" 
					placeholder="用户账号" clearable :disabled=" false  ||ro.yonghuzhanghao"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="密码" prop="mima">
				<el-input v-model="ruleForm.mima" 
					placeholder="密码" clearable :disabled=" false  ||ro.mima"></el-input>
			</el-form-item>
			<el-form-item class="add-item" label="用户姓名" prop="yonghuxingming">
				<el-input v-model="ruleForm.yonghuxingming" 
					placeholder="用户姓名" clearable :disabled=" false  ||ro.yonghuxingming"></el-input>
			</el-form-item>
			<el-form-item class="add-item"  label="性别" prop="xingbie">
				<el-select v-model="ruleForm.xingbie" placeholder="请选择性别" :disabled=" false  ||ro.xingbie" >
					<el-option
						v-for="(item,index) in xingbieOptions"
						:key="index"
						:label="item"
						:value="item">
					</el-option>
				</el-select>
			</el-form-item>
			<el-form-item class="add-item" label="头像" v-if="type!='cross' || (type=='cross' && !ro.touxiang)" prop="touxiang">
				<file-upload
					tip="点击上传头像"
					action="file/upload"
					:limit="3"
					:multiple="true"
					:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
					@change="touxiangUploadChange"
					></file-upload>
			</el-form-item>
			<el-form-item class="add-item" v-else label="头像" prop="touxiang">
				<img v-if="ruleForm.touxiang.substring(0,4)=='http'" class="upload-img" v-bind:key="index" :src="ruleForm.touxiang.split(',')[0]">
				<img v-else class="upload-img" v-bind:key="index" v-for="(item,index) in ruleForm.touxiang.split(',')" :src="baseUrl+item">
			</el-form-item>
			<el-form-item class="add-item" label="联系方式" prop="lianxifangshi">
				<el-input v-model="ruleForm.lianxifangshi" 
					placeholder="联系方式" clearable :disabled=" false  ||ro.lianxifangshi"></el-input>
			</el-form-item>

			<el-form-item class="add-btn-item">
				<el-button class="submitBtn"  type="primary" @click="onSubmit">
					<span class="icon iconfont icon-kaitongfuwu"></span>
					<span class="text">确定</span>
				</el-button>
				<el-button class="closeBtn" @click="back()">
					<span class="icon iconfont icon-shanchu1"></span>
					<span class="text">取消</span>
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				id: '',
				baseUrl: '',
				ro:{
					yonghuzhanghao : false,
					mima : false,
					yonghuxingming : false,
					xingbie : false,
					touxiang : false,
					lianxifangshi : false,
					sfsh : false,
					shhf : false,
				},
				type: '',
				userTableName: localStorage.getItem('UserTableName'),
				ruleForm: {
					yonghuzhanghao: '',
					mima: '',
					yonghuxingming: '',
					xingbie: '',
					touxiang: '',
					lianxifangshi: '',
				},
				xingbieOptions: [],


				rules: {
					yonghuzhanghao: [
						{ required: true, message: '用户账号不能为空', trigger: 'blur' },
					],
					mima: [
						{ required: true, message: '密码不能为空', trigger: 'blur' },
					],
					yonghuxingming: [
						{ required: true, message: '用户姓名不能为空', trigger: 'blur' },
					],
					xingbie: [
					],
					touxiang: [
					],
					lianxifangshi: [
						{ validator: this.$validate.isMobile, trigger: 'blur' },
					],
					sfsh: [
					],
					shhf: [
					],
				},
				centerType: false,
			};
		},
		computed: {



		},
		components: {
		},
		created() {
			if(this.$route.query.centerType){
				this.centerType = true
			}
			//this.bg();
			let type = this.$route.query.type ? this.$route.query.type : '';
			this.init(type);
			this.baseUrl = this.$config.baseUrl;
		},
		methods: {
			getMakeZero(s) {
				return s < 10 ? '0' + s : s;
			},
			// 下载
			download(file){
				window.open(`${file}`)
			},
			// 初始化
			init(type) {
				this.type = type;
				if(type=='cross'){
					var obj = JSON.parse(localStorage.getItem('crossObj'));
					for (var o in obj){
						if(o=='yonghuzhanghao'){
							this.ruleForm.yonghuzhanghao = obj[o];
							this.ro.yonghuzhanghao = true;
							continue;
						}
						if(o=='mima'){
							this.ruleForm.mima = obj[o];
							this.ro.mima = true;
							continue;
						}
						if(o=='yonghuxingming'){
							this.ruleForm.yonghuxingming = obj[o];
							this.ro.yonghuxingming = true;
							continue;
						}
						if(o=='xingbie'){
							this.ruleForm.xingbie = obj[o];
							this.ro.xingbie = true;
							continue;
						}
						if(o=='touxiang'){
							this.ruleForm.touxiang = obj[o].split(",")[0];
							this.ro.touxiang = true;
							continue;
						}
						if(o=='lianxifangshi'){
							this.ruleForm.lianxifangshi = obj[o];
							this.ro.lianxifangshi = true;
							continue;
						}
					}
				}else if(type=='edit'){
					this.info()
				}
				// 获取用户信息
				this.$http.get(this.userTableName + '/session', {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						var json = res.data.data;
					}
				});
				this.xingbieOptions = "男,女".split(',')

				if (localStorage.getItem('raffleType') && localStorage.getItem('raffleType') != null) {
					localStorage.removeItem('raffleType')
					setTimeout(() => {
						this.onSubmit()
					}, 300)
				}
			},

			// 多级联动参数
			// 多级联动参数
			info() {
				this.$http.get(`yonghu/detail/${this.$route.query.id}`, {emulateJSON: true}).then(res => {
					if (res.data.code == 0) {
						this.ruleForm = res.data.data;
					}
				});
			},
			// 提交
			async onSubmit() {
				await this.$refs["ruleForm"].validate(async valid => {
					if(valid) {
						if(this.type=='cross'){
							var statusColumnName = localStorage.getItem('statusColumnName');
							var statusColumnValue = localStorage.getItem('statusColumnValue');
							if(statusColumnName && statusColumnName!='') {
								var obj = JSON.parse(localStorage.getItem('crossObj'));
								if(!statusColumnName.startsWith("[")) {
									for (var o in obj){
										if(o==statusColumnName){
											obj[o] = statusColumnValue;
										}
									}
									var table = localStorage.getItem('crossTable');
									await this.$http.post(table+'/update', obj).then(res => {});
								}
							}
						}


						await this.$http.post(`yonghu/${this.ruleForm.id?'update':this.centerType?'save':'add'}`, this.ruleForm).then(async res => {
							if (res.data.code == 0) {
								this.$message({
									message: '操作成功',
									type: 'success',
									duration: 1500,
									onClose: () => {
										this.$router.go(-1);
										
									}
								});
							} else {
								this.$message({
									message: res.data.msg,
									type: 'error',
									duration: 1500
								});
							}
						});
					}
				});
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 返回
			back() {
				this.$router.go(-1);
			},
			touxiangUploadChange(fileUrls) {
				this.ruleForm.touxiang = fileUrls.replace(new RegExp(this.$config.baseUrl,"g"),"");
			},
		}
	};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.add-update-preview {
		padding: 20px 16%;
		margin: 0px auto;
		background: #F2F3F7;
		width: 100%;
		position: relative;
		.add-update-form {
			width: 100%;
			position: relative;
			.add-item.el-form-item {
				padding: 10px;
				margin: 0 0 10px;
				background: none;
				display: inline-block;
				width: 48%;
				/deep/ .el-form-item__label {
					padding: 0 10px 0 0;
					color: #666;
					font-weight: 500;
					width: 150px;
					font-size: 14px;
					line-height: 40px;
					text-align: center;
				}
				/deep/ .el-form-item__content {
					margin-left: 150px;
				}
				.el-input {
					width: 100%;
				}
				.el-input /deep/ .el-input__inner {
					border: 1px solid #E2E3E5;
					border-radius: 0;
					padding: 0 12px;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					color: #000;
					width: 80%;
					font-size: 15px;
					height: 40px;
				}
				.el-input /deep/ .el-input__inner[readonly="readonly"] {
					border: 1px solid #E2E3E5;
					cursor: not-allowed;
					border-radius: 0;
					padding: 0 12px;
					box-shadow: 0 0 0px rgba(85, 85, 127, 0.5);
					outline: none;
					color: #000;
					background: #f8f8f8;
					width: 80%;
					font-size: 15px;
					height: 40px;
				}
				.el-input-number /deep/ .el-input__inner {
					text-align: left;
					border: 1px solid #E2E3E5;
					border-radius: 0;
					padding: 0 12px;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					color: #000;
					width: 80%;
					font-size: 15px;
					height: 40px;
				}
				.el-input-number /deep/ .is-disabled .el-input__inner {
					text-align: left;
					border: 1px solid #E2E3E5;
					cursor: not-allowed;
					border-radius: 0;
					padding: 0 12px;
					box-shadow: 0 0 0px rgba(85, 85, 127, 0.5);
					outline: none;
					color: #000;
					background: #f8f8f8;
					width: 80%;
					font-size: 15px;
					height: 40px;
				}
				.el-input-number /deep/ .el-input-number__decrease {
					display: none;
				}
				.el-input-number /deep/ .el-input-number__increase {
					display: none;
				}
				.el-select {
					width: 80%;
				}
				.el-select /deep/ .el-input__inner {
					border: 1px solid #E2E3E5;
					border-radius: 0;
					padding: 0 10px;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					color: #000;
					width: 100%;
					font-size: 15px;
					height: 40px;
				}
				.el-select /deep/ .is-disabled .el-input__inner {
					border: 1px solid #E2E3E5;
					cursor: not-allowed;
					border-radius: 0;
					padding: 0 10px;
					box-shadow: 0 0 0px rgba(85, 85, 127, 0.5);
					outline: none;
					color: #000;
					background: #f8f8f8;
					width: 100%;
					font-size: 15px;
					height: 40px;
				}
				.el-date-editor {
					width: 80%;
				}
				.el-date-editor /deep/ .el-input__inner {
					border: 1px solid #E2E3E5;
					border-radius: 0;
					padding: 0 10px 0 30px;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					color: #000;
					width: 100%;
					font-size: 15px;
					height: 40px;
				}
				.el-date-editor /deep/ .el-input__inner[readonly="readonly"] {
					border: 1px solid #E2E3E5;
					cursor: not-allowed;
					border-radius: 0;
					padding: 0 10px 0 30px;
					box-shadow: 0 0 0px rgba(85, 85, 127, 0.5);
					outline: none;
					color: #000;
					background: #f8f8f8;
					width: 100%;
					font-size: 15px;
					height: 40px;
				}
				/deep/ .el-upload--picture-card {
					background: transparent;
					border: 0;
					border-radius: 0;
					width: auto;
					height: auto;
					line-height: initial;
					vertical-align: middle;
				}
				/deep/ .upload .upload-img {
					border: 1px dashed #999;
					cursor: pointer;
					border-radius: 6px;
					color: #999;
					width: 100px;
					font-size: 32px;
					line-height: 100px;
					text-align: center;
					height: 100px;
				}
				/deep/ .el-upload-list .el-upload-list__item {
					border: 1px dashed #999;
					cursor: pointer;
					border-radius: 6px;
					color: #999;
					width: 100px;
					font-size: 32px;
					line-height: 100px;
					text-align: center;
					height: 100px;
					font-size: 14px;
					line-height: 1.8;
				}
				/deep/ .el-upload .el-icon-plus {
					border: 1px dashed #999;
					cursor: pointer;
					border-radius: 6px;
					color: #999;
					width: 100px;
					font-size: 32px;
					line-height: 100px;
					text-align: center;
					height: 100px;
				}
				/deep/ .el-upload__tip {
					color: #838fa1;
					display: none;
				}
				.el-textarea /deep/ .el-textarea__inner {
					border: 1px solid #E2E3E5;
					border-radius: 0;
					padding: 12px;
					box-shadow: 0 0 0px rgba(64, 158, 255, .5);
					outline: none;
					color: #000;
					width: 80%;
					font-size: 14px;
					height: 120px;
				}
				.el-textarea /deep/ .el-textarea__inner[readonly="readonly"] {
					border: 1px solid #E2E3E5;
					cursor: not-allowed;
					border-radius: 4px;
					padding: 12px;
					box-shadow: 0 0 0px rgba(85, 85, 127, 0.5);
					outline: none;
					color: #000;
					background: #f8f8f8;
					width: 80%;
					font-size: 14px;
					height: 120px;
				}
				/deep/ .el-input__inner::placeholder {
					color: #999;
					font-size: 14px;
				}
				/deep/ textarea::placeholder {
					color: #999;
					font-size: 14px;
				}
				.editor {
					background-color: #fff;
					border-radius: 0;
					padding: 0;
					box-shadow: 0 0 0px rgba(75,223,201,.5);
					margin: 0;
					width: 80%;
					border-color: #ccc;
					border-width: 0;
					border-style: solid;
					height: auto;
				}
				.upload-img {
					width: 100px;
					height: 100px;
				}
				.viewBtn {
					border: 0;
					cursor: pointer;
					padding: 0 10px;
					margin: 0;
					color: #fff;
					display: inline-block;
					font-size: 14px;
					line-height: 30px;
					border-radius: 4px;
					outline: none;
					background: #5AC3B2;
					width: auto;
					height: 30px;
				}
				.viewBtn:hover {
					color: #fff;
					background: #5AC3B290;
				}
				.unviewBtn {
					border: 0;
					cursor: not-allowed;
					padding: 0 10px;
					margin: 0;
					color: #ccc;
					display: inline-block;
					font-size: 14px;
					line-height: 30px;
					border-radius: 4px;
					outline: none;
					background: #858585;
					width: auto;
					height: 30px;
				}
				.unviewBtn:hover {
					color: #ccc;
					background: #858585;
				}
			}
			.add-btn-item {
				padding: 0;
				margin: 0;
				.submitBtn {
					border: 0;
					cursor: pointer;
					border-radius: 4px;
					padding: 0 15px;
					margin: 0 20px 0 0;
					outline: none;
					background: #5AC3B2;
					display: inline-block;
					width: auto;
					font-size: 14px;
					line-height: 30px;
					height: 30px;
					.icon {
						color: rgba(255, 255, 255, 1);
						display: none;
					}
					.text {
						color: rgba(255, 255, 255, 1);
					}
				}
				.submitBtn:hover {
					background: #5AC3B290;
					.icon {
						color: #fff;
					}
					.text {
						color: #fff;
					}
				}
				.closeBtn {
					border: 0px solid rgba(64, 158, 255, 1);
					cursor: pointer;
					padding: 0 15px;
					margin: 0 20px 0 0;
					display: inline-block;
					font-size: 14px;
					line-height: 30px;
					border-radius: 4px;
					outline: none;
					background: #858585;
					width: auto;
					text-align: center;
					min-width: 100px;
					height: 30px;
					.icon {
						color: #fff;
						display: none;
					}
					.text {
						color: #fff;
					}
				}
				.closeBtn:hover {
					background: #85858590;
					.icon {
						color: #fff;
					}
					.text {
						color: #fff;
					}
				}
			}
		}
	}
	.el-date-editor.el-input {
		width: auto;
	}
</style>
