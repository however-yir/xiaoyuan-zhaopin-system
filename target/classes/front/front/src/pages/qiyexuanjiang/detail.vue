<template>
	<div>
	<!--  -->
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'-'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index" :to="'/index/qiyexuanjiang?centerType=' + (centerType?'1':'0')"><a>{{item.name}}</a></el-breadcrumb-item>
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
							{{detail.biaoti}}
						</div>
						<div class="colectBtn" @click="storeup(1)" v-show="!isStoreup">
							<i class="icon iconfont icon-shoucang10"></i>
							<span class="text">收藏({{detail.storeupnum}})</span>
						</div>
						<div class="colectBtnActive" @click="storeup(-1)" v-show="isStoreup">
							<i class="icon iconfont icon-shoucang12"></i>
							<span class="text">已收藏({{detail.storeupnum}})</span>
						</div>
					</div>
					<div class="item">
						<div class="lable">企业名称</div>
						<div class="text "  >{{detail.qiyemingcheng}}</div>
					</div>
					<div class="item">
						<div class="lable">企业地址</div>
						<div class="text "  >{{detail.qiyedizhi}}</div>
					</div>
					<div class="item">
						<div class="lable">成立时间</div>
						<div class="text "  >{{detail.chenglishijian}}</div>
					</div>
					<div class="item">
						<div class="lable">企业账号</div>
						<div class="text "  >{{detail.qiyezhanghao}}</div>
					</div>
					<div class="item">
						<div class="lable">法人代表</div>
						<div class="text "  >{{detail.farendaibiao}}</div>
					</div>
					<div class="item">
						<div class="lable">发布时间</div>
						<div class="text "  >{{detail.fabushijian}}</div>
					</div>
					<div class="btn_box">
						<el-button class="editBtn" v-if="btnAuth('qiyexuanjiang','修改')" @click="editClick">修改</el-button>
						<el-button class="delBtn" v-if="btnAuth('qiyexuanjiang','删除')" @click="delClick">删除</el-button>
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
				<el-tab-pane label="企业宣言" name="first">
					<div class="ql-snow ql-editor" v-html="detail.qiyexuanyan"></div>
				</el-tab-pane>
			</el-tabs>

		</div>
		<div class="share_view">
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
				tablename: 'qiyexuanjiang',
				baseUrl: '',
				breadcrumbItem: [
					{
						name: '企业宣讲'
					}
				],
				title: '',
				detailBanner: [],
				userid: localStorage.getItem('frontUserid'),
				id: 0,
				detail: {},
				tabsNum: 1,
				activeName: 'first',
				total: 1,
				pageSize: 5,
				totalPage: 1,
				storeupParams: {
					name: '',
					picture: '',
					refid: 0,
					tablename: 'qiyexuanjiang',
					userid: localStorage.getItem('frontUserid')
				},
				isStoreup: false,
				storeupInfo: {},
				buynumber: 1,
				centerType: false,
				storeupType: false,
				shareUrl: location.href,
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
						this.title = this.detail.biaoti;
						if(this.detail.fengmian) {
							this.detailBanner = this.detail.fengmian.split(",w").length>1?[this.detail.fengmian]:this.detail.fengmian.split(',');
						}
						this.$forceUpdate();
						if(localStorage.getItem('frontToken')){
							this.getStoreupStatus();
						}

					}
				});
			},
			storeup(type) {
				if (type == 1 && !this.isStoreup) {
					this.storeupParams.name = this.title;
					this.storeupParams.picture = this.detailBanner[0];
					this.storeupParams.refid = this.detail.id;
					this.storeupParams.type = type;
					this.$http.post('storeup/add', this.storeupParams).then(res => {
						if (res.data.code == 0) {
							this.isStoreup = true;
							this.detail.storeupnum++
							this.$http.post('qiyexuanjiang/update', this.detail).then(res => {});
							this.$message({
								type: 'success',
								message: '收藏成功!',
								duration: 1500,
							});
						}
					});
				}
				if (type == -1 && this.isStoreup) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'qiyexuanjiang', userid: localStorage.getItem('frontUserid')}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
							let delIds = new Array();
							delIds.push(this.storeupInfo.id);
							this.$http.post('storeup/delete', delIds).then(res => {
								if (res.data.code == 0) {
									this.isStoreup = false;
									this.detail.storeupnum--
									this.$http.post('qiyexuanjiang/update', this.detail).then(res => {});
									this.$message({
										type: 'success',
										message: '取消成功!',
										duration: 1500,
									});
								}
							});
						}
					});
				}
			},
			getStoreupStatus(){
				if(localStorage.getItem("frontToken")) {
					this.$http.get('storeup/list', {params: {page: 1, limit: 1, type: 1, refid: this.detail.id, tablename: 'qiyexuanjiang', userid: localStorage.getItem('frontUserid')}}).then(res => {
						if (res.data.code == 0 && res.data.data.list.length > 0) {
							this.isStoreup = true;
							this.storeupInfo = res.data.data.list[0];
						}
					});
				}
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
					this.$router.push({path: '/index/qiyexuanjiang', query: params});
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
				this.$router.push(`/index/qiyexuanjiangAdd?type=edit&&id=${this.detail.id}`);
			},
			// 删除
			async delClick(){
				await this.$confirm('是否删除此企业宣讲？') .then(_ => {
					this.$http.post('qiyexuanjiang/delete', [this.detail.id]).then(async res => {
						if (res.data.code == 0) {
							this.$http.get('storeup/list',{params:{
								page: 1,
								limit: 100,
								refid: this.detail.id,
								tablename: 'qiyexuanjiang',
							}}).then(async obj=>{
								if(obj.data&&obj.data.code==0){
									let arr = []
									for(let x in obj.data.data.list){
										arr.push(obj.data.data.list[x].id)
									}
									if(arr.length){
										await this.$http.post('storeup/delete',arr).then(()=>{})
									}
									this.$message({
										type: 'success',
										message: '删除成功!',
										duration: 1500,
										onClose: () => {
											history.back()
										}
									});
								}
							})
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
					.colectBtn {
						padding: 0;
						background: #fff;
						.icon {
							color: #0AB8C1;
							font-size: 24px;
						}
						.text {
							color: #999;
							display: none;
							font-size: 14px;
						}
					}
					.colectBtnActive {
						background: none;
						.icon {
							color: #0AB8C1;
						}
						.text {
							color: #fff;
						}
					}
					.colectBtn:hover {
						background: none;
						.icon {
							color: #0AB8C150;
						}
						.text {
							color: #000;
						}
					}
					.colectBtnActive:hover {
						background: none;
						.icon {
							color: #0AB8C150;
						}
						.text {
							color: #000;
						}
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
	.share_view{
		box-shadow: 0 1px 6px rgba(0,0,0,.3);
		z-index: 11;
		bottom: 20%;
		background: #fff;
		position: fixed;
		right: 0;
		.share:last-of-type{
			border:none;
		}
	}
</style>
