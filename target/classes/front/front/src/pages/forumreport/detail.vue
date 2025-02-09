<template>
	<div>
	<!--  -->
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'-'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/forumreport?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item3"><a href="javascript:void(0);">详情</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
		<div class="back_box">
			<el-button class="backBtn" size="mini" @click="backClick">
				<span class="icon iconfont icon-jiantou33"></span>
				<span class="text">返回</span>
			</el-button>
		</div>
		<div class="detail-preview">
			<div class="attr">
				<div class="info">
					<div class="title-item">
						<div class="detail-title">
						</div>
					</div>
					<div class="item">
						<div class="lable">论坛id</div>
						<div class="text "  >{{detail.forumid}}</div>
					</div>
					<div class="item">
						<div class="lable">帖子标题</div>
						<div class="text "  >{{detail.title}}</div>
					</div>
					<div class="item">
						<div class="lable">举报用户名</div>
						<div class="text "  >{{detail.username}}</div>
					</div>
					<div class="item">
						<div class="lable">被举报用户id</div>
						<div class="text "  >{{detail.reporteduserid}}</div>
					</div>
					<div class="item">
						<div class="lable">被举报用户名</div>
						<div class="text "  >{{detail.reportedusername}}</div>
					</div>
					<div class="item">
						<div class="lable">举报原因</div>
						<div class="text "  >{{detail.reason}}</div>
					</div>
					<div class="item">
						<div class="lable">处理建议</div>
						<div class="text "  >{{detail.handleadvise}}</div>
					</div>
					<div class="item">
						<div class="lable">举报类型</div>
						<div class="text "  >{{detail.reporttype}}</div>
					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('forumreport','修改')" @click="editClick">修改</el-button>
						<el-button class="delBtn" v-if="btnAuth('forumreport','删除')" @click="delClick">删除</el-button>
					</div>
				</div>
			</div>
		
			<el-carousel v-if="detailBanner.length" trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="600px" :autoplay="false" :interval="3000" :loop="true">
				<el-carousel-item v-for="item in detailBanner" :key="item.id">
					<img :preview-src-list="[item]" v-if="item.substr(0,4)=='http'" :src="item" class="image">
					<img :preview-src-list="[baseUrl + item]" v-else :src="baseUrl + item" class="image">
				</el-carousel-item>
			</el-carousel>


		

			<el-tabs class="detail-tabs" v-model="activeName" type="border-card" v-if="tabsNum>0">
			</el-tabs>

		</div>
	</div>
</template>

<script>
	import axios from 'axios'
	import Swiper from "swiper";
	export default {
		//数据集合
		data() {
			return {
				tablename: 'forumreport',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '交流论坛举报'
					}
				],
				title: '',
				detailBanner: [],
				userid: localStorage.getItem('frontUserid'),
				id: 0,
				detail: {},
				tabsNum: 0,
				activeName: 'first',
				total: 1,
				pageSize: 5,
				totalPage: 1,
				buynumber: 1,
				centerType: false,
				storeupType: false,
			}
		},
		created() {
			if(this.$route.query.centerType&&this.$route.query.centerType!=0) {
				this.centerType = true
			}
			if(this.$route.query.storeupType&&this.$route.query.storeupType!=0) {
				this.storeupType = true
			}
			
			this.init();
		},
		mounted() {
		},
		//方法集合
		methods: {
			init() {
				this.id = this.$route.query.id
				this.baseUrl = this.$config.baseUrl;
				this.$http.get(this.tablename + '/detail/'  + this.id, {}).then(res => {
					if (res.data.code == 0) {
						this.detail = res.data.data;
						this.$forceUpdate();
						if(localStorage.getItem('frontToken')){
						}

					}
				});
			},
			curChange(page) {
				this.getDiscussList(page);
			},
			prevClick(page) {
				this.getDiscussList(page);
			},
			nextClick(page) {
				this.getDiscussList(page);
			},
			sizeChange(size){
				this.pageSize = size
				this.getDiscussList(1);
			},
			// 返回按钮
			backClick(){
				if(this.storeupType){
					history.back()
				}else{
					let params = {}
					if(this.centerType){
						params.centerType = 1
					}
					this.$router.push({path: '/index/forumreport', query: params});
				}
			},
			// 下载
			download(file ){
				if(!file) {
					this.$message({
						type: 'error',
						message: '文件不存在',
						duration: 1500,
					});
					return;
				}
				let arr = file.replace(new RegExp('upload/', "g"), "")
				axios.get(this.baseUrl + '/file/download?fileName=' + arr, {
					headers: {
						token: localStorage.getItem("frontToken")
					},
					responseType: "blob"
				}).then(({
					data
				}) => {
					const binaryData = [];
					binaryData.push(data);
					const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
						type: 'application/pdf;chartset=UTF-8'
					}))
					const a = document.createElement('a')
					a.href = objectUrl
					a.download = arr
					// a.click()
					// 下面这个写法兼容火狐
					a.dispatchEvent(new MouseEvent('click', {
						bubbles: true,
						cancelable: true,
						view: window
					}))
					window.URL.revokeObjectURL(data)
				},err=>{
					axios.get((location.href.split(this.$config.name).length>1 ? location.href.split(this.$config.name)[0] :'') + this.$config.name + '/file/download?fileName=' + arr, {
						headers: {
							token: localStorage.getItem("frontToken")
						},
						responseType: "blob"
					}).then(({
						data
					}) => {
						const binaryData = [];
						binaryData.push(data);
						const objectUrl = window.URL.createObjectURL(new Blob(binaryData, {
							type: 'application/pdf;chartset=UTF-8'
						}))
						const a = document.createElement('a')
						a.href = objectUrl
						a.download = arr
						// a.click()
						// 下面这个写法兼容火狐
						a.dispatchEvent(new MouseEvent('click', {
							bubbles: true,
							cancelable: true,
							view: window
						}))
						window.URL.revokeObjectURL(data)
					})
				})
			},


			// 权限判断
			btnAuth(tableName,key){
				if(this.centerType){
					return this.isBackAuth(tableName,key)
				}else{
					return this.isAuth(tableName,key)
				}
			},
			// 修改
			editClick(){
				this.$router.push(`/index/forumreportAdd?type=edit&&id=${this.detail.id}`);
			},
			// 删除
			async delClick(){
				await this.$confirm('是否删除此交流论坛举报？') .then(_ => {
					this.$http.post('forumreport/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$message({
								type: 'success',
								message: '删除成功!',
								duration: 1500,
								onClose: () => {
									history.back()
								}
							});
						}
					});
				}).catch(_ => {});
			},
		},
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.detail-preview {
		padding: 20px 16%;
		margin: 10px auto;
		background: #F2F3F7;
		display: flex;
		width: 100%;
		position: relative;
		flex-wrap: wrap;
		.attr {
			padding: 20px;
			background: #fff;
			flex: 1;
			display: flex;
			width: 100%;
			min-height: 600px;
			position: relative;
			order: 2;
			.info {
				padding: 0;
				margin: 0;
				background: #fff;
				width: 100%;
				.title-item {
					padding: 0 10px;
					margin: 0 0 0px 0;
					background: none;
					display: flex;
					justify-content: space-between;
					align-items: center;
					.detail-title {
						color: #000;
						font-size: 24px;
					}
				}
				.item {
					padding: 8px 10px;
					margin: 0 0 0px 0;
					display: flex;
					border-color: #D8D8D8;
					border-width: 0 0 2px;
					justify-content: spaceBetween;
					border-style: solid;
					.lable {
						padding: 0 10px;
						color: #9E9E9E;
						width: 120px;
						font-size: 13px;
						line-height: 24px;
						text-align: center;
					}
					.text {
						padding: 0 10px;
						color: #000;
						flex: 1;
						font-size: 15px;
						line-height: 24px;
						height: auto;
					}
					.price {
						color: #f00;
					}
					.bold {
						font-weight: bold;
					}
					.link {
						cursor: pointer;
						text-decoration: underline;
					}
				}
				.btn_box {
					padding: 10px 0;
					display: flex;
					flex-wrap: wrap;
				}
				.editBtn {
					border: 0;
					cursor: pointer;
					border-radius: 10px;
					padding: 0 10px;
					margin: 0 10px 0 0;
					outline: none;
					color: #fff;
					background: #0AB8C1;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.editBtn:hover {
					background: #0AB8C190;
				}
				.delBtn {
					border: 0;
					cursor: pointer;
					border-radius: 10px;
					padding: 0 10px;
					margin: 0 10px 0 0;
					outline: none;
					color: #fff;
					background: #0AB8C1;
					width: auto;
					font-size: 14px;
					line-height: 40px;
					height: 40px;
				}
				.delBtn:hover {
					background: #0AB8C190;
				}
			}
		}
		.el-carousel {
			padding: 15px;
			background: #fff;
			width: 50%;
			height: auto;
			order: 1;
			/deep/ .el-carousel__indicator button {
				width: 0;
				height: 0;
				display: none;
			}
			/deep/ .el-carousel__container {
				.el-carousel__arrow--left {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--left:hover {
					background: red;
				}
				.el-carousel__arrow--right {
					width: 36px;
					font-size: 12px;
					height: 36px;
				}
				.el-carousel__arrow--right:hover {
					background: red;
				}
				.el-carousel__item {
					border-radius: 10px;
					width: 100%;
					height: 100%;
					img {
						object-fit: cover;
						width: 100%;
						height: 100%;
					}
				}
			}
		
			/deep/ .el-carousel__indicators {
				padding: 0;
				margin: 0;
				z-index: 2;
				position: absolute;
				list-style: none;
				li {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 12px;
					opacity: 0.4;
					transition: 0.3s;
					height: 12px;
				}
				li:hover {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 24px;
					opacity: 0.7;
					height: 12px;
				}
				li.is-active {
					padding: 0;
					margin: 0 4px;
					background: #fff;
					display: inline-block;
					width: 24px;
					opacity: 1;
					height: 12px;
				}
			}
		}
		.detail-tabs {
			border: 0px solid #DCDFE6;
			box-shadow: 0 0px 0px 0 rgba(0, 0, 0, .1);
			margin: 20px 0 0;
			background: #F2F3F7;
			width: 100%;
			order: 4;
			& /deep/ .el-tabs__header .el-tabs__nav-wrap {
				margin-bottom: 0;
			}
			/deep/ .el-tabs__header {
				margin: 0;
				background: #0AB8C1;
				border-color: #E4E7ED;
				border-width: 0;
				border-style: solid;
			}
			
			/deep/ .el-tabs__header .el-tabs__item {
				border: 0;
				padding: 0 20px;
				margin: 10px 20px;
				color: #fff;
				font-weight: 500;
				display: inline-block;
				font-size: 14px;
				line-height: 40px;
				border-radius: 10px;
				background: transparent;
				position: relative;
				list-style: none;
				height: 40px;
			}
			
			/deep/ .el-tabs__header .el-tabs__item:hover {
				border: 0;
				border-radius: 10px;
				margin: 10px 20px;
				color: #0AB8C1;
				background: #FFF;
			}
			
			/deep/ .el-tabs__header .el-tabs__item.is-active {
				border: 0;
				border-radius: 10px;
				margin: 10px 20px;
				color: #0AB8C1;
				background: #FFF;
			}
			
			/deep/ .el-tabs__content {
				padding: 0;
				margin: 20px 0;
				background: #fff;
			}
		}
	}
</style>
