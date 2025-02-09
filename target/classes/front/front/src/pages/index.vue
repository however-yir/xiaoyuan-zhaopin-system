<template>
	<div class="main-containers">
		<div class="body-containers">
			<div class="top-container">
				<!-- info -->
				<div class="top_title">
					<span @click="goMenu('/index/home')">基于SpringBoot的校园招聘推荐系统</span>
				</div>
				<div class="top_tel"></div>
			

				<img class="top_avatar1" v-if="headportrait&&Token" :src="headportrait?baseUrl + headportrait:require('@/assets/avator.png')">
				<div class="top_nickname1" v-if="Token">{{username}}</div>
				<el-button v-if="Token && notAdmin" class="btn-user" @click="goMenu('/index/center')">
					<span class="icon iconfont icon-wuliu8"></span>
					个人中心
				</el-button>
				<el-button v-if="!Token" class="btn-login" @click="toLogin">
					<span class="icon iconfont icon-touxiang03"></span>
					登录
				</el-button>
				<el-button v-if="Token" class="btn-register" @click="logout">
					<span class="icon iconfont icon-guanbi19"></span>
					退出
				</el-button>
			</div>


			<div class="menu-preview">
				<div class="menu-list">
					<div class="menu-home" :class="activeMenu=='/index/home'?'menu-active':''" @click="goMenu('/index/home')">
						<div class="title">
							<span class="icon iconfont icon-shouye-zhihui"></span>
							<div class="text">系统首页</div>
						</div>
					</div>
					<div class="menu-item" v-for="(item,index) in menuList" :key="index" @mouseenter="menuShowClick4(index)" @mouseleave="menuShowClick4(-1)" :class="activeMenu==item.url?'menu-active':''" @click.stop="goMenu(item.url)">
						<div class="title">
							<span :class="iconArr[index]"></span>
							<div class="text">{{item.name}}</div>
						</div>
						<transition name="el-zoom-in-top">
							<div v-if="showType4==index&&item.hasCate" class="menu-child-list">
								<div class="child-item" v-for="(items,indexs) in item.cateList" :key="indexs" @click.stop="cateClick(item.url,items)">{{items}}</div>
							</div>
						</transition>
					</div>
					<div class="menu-user" :class="activeMenu=='/index/center'?'menu-active':''" @click="goMenu('/index/center')" v-if="Token && notAdmin">
						<div class="title">
							<span class="icon iconfont icon-shouye-zhihui"></span>
							<div class="text">个人中心</div>
						</div>
					</div>
				</div>
			</div>

			<div class="banner-preview" v-if="carouselChange()">
				<el-carousel trigger="click" indicator-position="inside" arrow="always" type="default" direction="horizontal" height="400px" :autoplay="false" :interval="3000" :loop="true">
					<el-carousel-item v-for="item in carouselList" :key="item.id">
						<el-image v-if="preHttp(item.value)" @click="carouselClick(item.url)" :src="item.value" fit="cover"></el-image>
						<el-image v-else @click="carouselClick(item.url)" :src="baseUrl + item.value" fit="cover"></el-image>
						<div class="banner-hidden">
						</div>
					</el-carousel-item>
				</el-carousel>
			</div>
			<router-view id="scrollView"></router-view>
			
			<div class="bottom-preview">
				<div class="footer"><div v-html="bottomContent"></div></div>
			</div>
		</div>
		
	</div>
</template>

<script>
	import Vue from 'vue'
	import Swiper from "swiper";
	import axios from 'axios'
export default {
	data() {
		return {
			activeIndex: '0',
			baseUrl: '',
			carouselList: [],
			carouselForm: {
				inHome: true,
				inOther: true,
			},
			menuList: [],
			headportrait: localStorage.getItem('frontHeadportrait')?localStorage.getItem('frontHeadportrait'):'',
			Token: localStorage.getItem('frontToken'),
			username: localStorage.getItem('username'),
			notAdmin: localStorage.getItem('frontSessionTable')!='"users"',
			iconArr: [
				'el-icon-star-off',
				'el-icon-goods',
				'el-icon-warning',
				'el-icon-question',
				'el-icon-info',
				'el-icon-help',
				'el-icon-picture-outline-round',
				'el-icon-camera-solid',
				'el-icon-video-camera-solid',
				'el-icon-video-camera',
				'el-icon-bell',
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-operation',
				'el-icon-s-promotion',
				'el-icon-s-release',
				'el-icon-s-ticket',
				'el-icon-s-management',
				'el-icon-s-open',
				'el-icon-s-shop',
				'el-icon-s-marketing',
				'el-icon-s-flag',
				'el-icon-s-comment',
				'el-icon-s-finance',
				'el-icon-s-claim',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check'
			],
			bottomContent: '',
			showType4: -1,
		}
	},
	async created() {
		this.baseUrl = this.$config.baseUrl;
		this.menuList = this.$config.indexNav;
		this.getCarousel();
		if(localStorage.getItem('frontToken') && localStorage.getItem('frontToken')!=null) {
			this.getSession()
		}
		this.cateList = this.$config.cateList
		if(this.cateList.length){
			for(let x in this.menuList){
				for(let i in this.cateList){
					if(this.menuList[x].name==this.cateList[i].name){
						await this.$http.get(`option/${this.cateList[i].refTable}/${this.cateList[i].refColumn}`).then(rs=>{
							this.menuList[x].cateList = rs.data.data
							this.menuList[x].hasCate = true
						})
					}
				}
			}
		}
	},
	mounted() {
		this.activeIndex = localStorage.getItem('keyPath') || '0';



	},
	computed: {
		activeMenu() {
			const route = this.$route
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		},
	},
	watch: {
		$route(newValue) {
			let that = this
			let url = window.location.href
			let arr = url.split('#')
			for (let x in this.menuList) {
				if (newValue.path == this.menuList[x].url) {
					this.activeIndex = x
				}
			}
			this.Token = localStorage.getItem('frontToken')
			if(arr[1]!='/index/home'){
				var element = document.getElementById('scrollView');
				var distance = element.offsetTop;
				window.scrollTo( 0, distance )
			}else{
				window.scrollTo( 0, 0 )
			}
		},
		headportrait(){
			this.$forceUpdate()
		},
	},
	methods: {
		cateClick(url,fenlei){
			this.$router.push(url + '?homeFenlei=' + fenlei);
		},
		preHttp(str) {
			return str && str.substr(0,4)=='http';
		},

		async getSession() {
			await this.$http.get(`${localStorage.getItem('UserTableName')}/session`, {emulateJSON: true}).then(async res => {
				if (res.data.code == 0) {
					localStorage.setItem('sessionForm',JSON.stringify(res.data.data))
					localStorage.setItem('frontUserid', res.data.data.id);
					if(res.data.data.vip) {
						localStorage.setItem('vip', res.data.data.vip);
					}
					if(res.data.data.touxiang) {
						this.headportrait = res.data.data.touxiang
						localStorage.setItem('frontHeadportrait', res.data.data.touxiang);
					} else if(res.data.data.headportrait) {
						this.headportrait = res.data.data.headportrait
						localStorage.setItem('frontHeadportrait', res.data.data.headportrait);
					}
				}
			});
		},
		handleSelect(keyPath) {
			if (keyPath) {
				localStorage.setItem('keyPath', keyPath)
			}
		},
		toLogin() {
		  this.$router.push('/login');
		},
		logout() {
			localStorage.clear();
			Vue.http.headers.common['Token'] = "";
			this.$router.push('/index/home');
			this.activeIndex = '0'
			localStorage.setItem('keyPath', this.activeIndex)
			this.Token = ''
			this.$forceUpdate()
			this.$message({
				message: '登出成功',
				type: 'success',
				duration: 1000,
			});
		},
		getCarousel() {
			this.$http.get('config/list', {params: { page: 1, limit: 3 }}).then(res => {
				if (res.data.code == 0) {
					this.carouselList = res.data.data.list;
				}
			});
		},
		// 轮播图跳转
		carouselClick(url) {
			if (url) {
				if (url.indexOf('https') != -1) {
					window.open(url)
				} else {
					this.$router.push(url)
				}
			}
		},
		carouselChange(){
			let url = window.location.href
			let arr = url.split('#')
			return (this.carouselForm.inHome&&arr[1]=='/index/home')||(this.carouselForm.inOther&&arr[1]!='/index/home')
		},
		goBackend() {
			localStorage.setItem('Token', localStorage.getItem('frontToken'));
			localStorage.setItem('role', localStorage.getItem('frontRole'));
			localStorage.setItem('sessionTable', localStorage.getItem('frontSessionTable'));
			localStorage.setItem('headportrait', localStorage.getItem('frontHeadportrait'));
			localStorage.setItem('userid', localStorage.getItem('frontUserid'));
			window.location.href = `${this.$config.baseUrl}admin/dist/index.html`
			
		},
		menuShowClick4(index){
			this.showType4 = index
		},
		goMenu(path) {
			this.$router.push(path);
		},
	}
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.main-containers {
		.body-containers {
			padding: 160px 0 0;
			margin: 0;
			background: #fff;
			min-height: 100vh;
			position: relative;
			.top-container {
				box-shadow: 0 0px 6px rgba(0,0,0,.3);
				padding: 60px 40px 30px 60px;
				z-index: 1002;
				top: 0;
				left: 0;
				background: #ffffff;
				display: flex;
				width: 100%;
				justify-content: flex-end;
				align-items: flex-end;
				position: absolute;
				height: 160px;
				.top_title {
					margin: 0 20px 0 0;
					white-space: nowrap;
					flex: 1;
					display: inline-block;
					order: -1;
					span {
						padding: 0 0 0 0;
						color: #000;
						white-space: absolute;
						font-size: 24px;
						line-height: 44px;
						float: left;
					}
				}
				.top_tel {
					margin: 0 0;
					color: #666;
					top: 60px;
					left: 0;
					background: #A7A7A7;
					width: 100%;
					font-size: 0;
					position: absolute;
					height: 1px;
				}
				.top_avatar1 {
					border-radius: 50%;
					margin: 0 12px;
					display: none;
					width: 40px;
					height: 40px;
				}
				.top_nickname1 {
					padding: 0 12px;
					margin: 0 10px 0px;
					color: #000;
					font-weight: bold;
					font-size: 16px;
					line-height: 44px;
					height: 44px;
					order: 5;
				}
				.btn-user {
					border: 0;
					padding: 0px;
					margin: 0 20px 0 0;
					color: #000;
					background: none;
					font-weight: bold;
					width: auto;
					font-size: 16px;
					line-height: 44px;
					height: 44px;
					order: 6;
					.icon {
						color: inherit;
						font-size: 14px;
					}
				}
				.btn-user:hover {
					opacity: 0.7;
				}
				.btn-login {
					border: 0;
					padding: 0 8px;
					margin: 0 10px;
					color: #333;
					font-weight: bold;
					font-size: 18px;
					line-height: 60px;
					right: 60px;
					top: 0;
					background: none;
					width: auto;
					position: absolute;
					height: 60px;
					.icon {
						padding: 0 5px 0 0;
						color: inherit;
						font-size: inherit;
					}
				}
				.btn-login:hover {
					opacity: 0.6;
				}
				.btn-register {
					border: 0;
					padding: 0 8px;
					margin: 0 10px;
					color: #333;
					font-weight: bold;
					font-size: 18px;
					line-height: 60px;
					right: 60px;
					top: 0;
					background: none;
					width: auto;
					position: absolute;
					height: 60px;
					.icon {
						padding: 0 5px 0 0;
						color: inherit;
						font-size: inherit;
					}
				}
				.btn-register:hover {
					opacity: 0.6;
				}
			}
			.menu-preview {
				.el-scrollbar {
					height: 100%;
			  
					& /deep/ .scrollbar-wrapper-vertical {
						overflow-x: hidden;
					}
			  
					& /deep/ .scrollbar-wrapper-horizontal {
						overflow-y: hidden;
			  
						.el-scrollbar__view {
							white-space: nowrap;
						}
					}
				}
				padding: 10px 0;
				background: #0AB8C1;
				width: 100%;
				.menu-list {
					display: flex;
					justify-content: center;
					position: relative;
					// 首页
					.menu-home {
						cursor: pointer;
						margin: 0 10px;
						color: #fff;
						line-height: 50px;
						height: 50px;
						.title {
							border-radius: 10px;
							padding: 0 20px;
							background: none;
							display: flex;
							height: 50px;
							.icon {
								padding: 0 10px;
								margin: 0;
								color: inherit;
								width: 14px;
								font-size: 16px;
								line-height: 50px;
								height: 50px;
							}
							.text {
								padding: 0 10px;
								color: inherit;
								font-size: 16px;
								line-height: 50px;
								height: 50px;
							}
						}
					}
					.menu-home:hover {
						.title {
							color: #0AB8C1;
							background: #fff;
						}
					}
					.menu-home.menu-active {
						.title {
							color: #0AB8C1;
							background: #fff;
							font-weight: bold;
						}
					}
					// 其他盒子
					.menu-item {
						margin: 0 10px;
						color: #fff;
						line-height: 50px;
						height: 50px;
						.title {
							cursor: pointer;
							border-radius: 10px;
							padding: 0 20px;
							background: none;
							display: flex;
							height: 50px;
							span {
								padding: 0 10px;
								margin: 0;
								color: inherit;
								width: 14px;
								font-size: 16px;
								line-height: 50px;
								height: 50px;
							}
							.text {
								padding: 0 10px;
								color: inherit;
								font-size: 16px;
								line-height: 50px;
								height: 50px;
							}
						}
						.menu-child-list {
							padding: 5px 0;
							z-index: 11;
							left: 0;
							background: #0AB8C1;
							bottom: -50px;
							display: flex;
							width: 100%;
							line-height: 40px;
							justify-content: center;
							position: absolute;
							height: 50px;
							.child-item {
								cursor: pointer;
								border-radius: 10px;
								padding: 0 20px;
								font-size: 14px;
							}
							.child-item:hover {
								color: #0AB8C1;
								background: #fff;
							}
						}
					}
					.menu-item:hover {
						.title {
							color: #0AB8C1;
							background: #fff;
						}
					}
					.menu-item.menu-active {
						.title {
							color: #0AB8C1;
							background: #fff;
							font-weight: bold;
						}
					}
					// 个人中心
					.menu-user {
						cursor: pointer;
						color: #fff;
						display: none;
						line-height: 50px;
						height: 50px;
						.title {
							cursor: pointer;
							border-radius: 10px;
							padding: 0 20px;
							background: none;
							display: flex;
							height: 50px;
							.icon {
								padding: 0 10px;
								margin: 0;
								color: inherit;
								width: 14px;
								font-size: 14px;
								line-height: 50px;
								height: 50px;
							}
							.text {
								padding: 0 10px;
								color: inherit;
								font-size: 16px;
								line-height: 50px;
								height: 50px;
							}
						}
					}
					.menu-user:hover {
						.title {
							color: #0AB8C1;
							background: #fff;
						}
					}
					.menu-user.menu-active {
						.title {
							color: #0AB8C1;
							background: #fff;
							font-weight: 600;
						}
					}
				}
			}
			.banner-preview {
				margin: 20px auto;
				width: 68%;
				height: auto;
				.el-carousel {
					margin: 0 auto;
					width: 100%;
					/deep/ .el-carousel__item {
						border-radius: 0;
						width: 100%;
						height: 100%;
						@keyframes wave1 {from { left: -236px } to { left: -1233px }}
						@keyframes wave2 {from { left: 0 } to { left: -1009px }}
						.el-image {
							object-fit: cover;
							width: 100%;
							height: 100%;
						}
					}
					/deep/ .el-carousel__container .el-carousel__arrow--left {
						width: 36px;
						font-size: 12px;
						height: 36px;
					}
					/deep/ .el-carousel__container .el-carousel__arrow--left:hover {
						background: #0AB8C1;
					}
					/deep/ .el-carousel__container .el-carousel__arrow--right {
						width: 36px;
						font-size: 12px;
						height: 36px;
					}
					/deep/ .el-carousel__container .el-carousel__arrow--right:hover {
						background: #0AB8C1;
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
							background: #0AB8C1;
							display: inline-block;
							width: 24px;
							opacity: 0.7;
							height: 12px;
						}
						li.is-active {
							padding: 0;
							margin: 0 4px;
							background: #0AB8C1;
							display: inline-block;
							width: 24px;
							opacity: 1;
							height: 12px;
						}
					}
					/deep/ .el-carousel__indicator button {
						width: 0;
						height: 0;
						display: none;
					}
				}
			}
			.bottom-preview {
				width: 100%;
				height: auto;
				.footer {
					padding: 20px;
					overflow: hidden;
					background: #2C2C2C;
					width: 100%;
					height: auto;
				}
			}
		}
	}
</style>
