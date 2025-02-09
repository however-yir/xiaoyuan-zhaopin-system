<template>
	<div>
		<div class="breadcrumb-preview">
			<el-breadcrumb :separator="'-'">
				<el-breadcrumb-item class="item1" to="/"><a>首页</a></el-breadcrumb-item>
				<el-breadcrumb-item class="item2" v-for="(item, index) in breadcrumbItem" :key="index"><a>{{item.name}}</a></el-breadcrumb-item>
			</el-breadcrumb>
		</div>
	
		<div class="news-preview-pv">
			<el-form :inline="true" :model="formSearch" class="list-form-pv">
				<el-form-item class="search-item">
					<el-input v-model="title" placeholder="标题"></el-input>
				</el-form-item>
				<el-button class="search-btn" type="primary" @click="getNewsList(1)">
					<span class="icon iconfont icon-chakan14"></span>
					搜索
				</el-button>
			</el-form>
			
			<!-- category -->
			<div class="category-list">
				<div class="item" @click="categoryClick(0)" :class="categoryIndex == 0 ? 'active' : ''">全部</div>
				<div v-for="(item,index) in categoryList" @click="categoryClick(index+1)" :key="index" class="item" :class="categoryIndex == index+1 ? 'active' : ''">{{item.typename}}</div>
			</div>
			<div class="list11">
				<div v-for="(item,index) in newsList" :key="index" :class="'list-item' + ((index%2) + 1)" @click="toNewsDetail(item)">
					<div class="img">
						<img class="image" :src="baseUrl + item.picture" >
					</div>
					<div class="infoBox">
						<div class="name">{{item.title}}</div>
						<div class="desc">{{item.introduction}}</div>
						<div class="infoCenter">
							<div class="publisher_item">
								<span class="icon iconfont" :class="index%2==0?'icon-geren16':'icon-geren16'"></span>
								<span class="label">{{index%2==0?'发布人：':'发布人：'}}</span>
								<span class="text">{{item.name}}</span>
							</div>
							<div class="like_item">
								<span class="icon iconfont" :class="index%2==0?'icon-zan10':'icon-zan10'"></span>
								<span class="label">{{index%2==0?'点赞数：':'点赞数：'}}</span>
								<span class="text">{{item.thumbsupnum}}</span>
							</div>
							<div class="collect_item">
								<span class="icon iconfont" :class="index%2==0?'icon-shoucang10':'icon-shoucang10'"></span>
								<span class="label">{{index%2==0?'收藏量：':'收藏量：'}}</span>
								<span class="text">{{item.storeupnum}}</span>
							</div>
							<div class="view_item">
								<span class="icon iconfont" :class="index%2==0?'icon-chakan9':'icon-chakan9'"></span>
								<span class="label">{{index%2==0?'点击量：':'点击量：'}}</span>
								<span class="text">{{item.clicknum}}</span>
							</div>
						</div>
						<div class="infoBottom">
							<div class="time_item">
								<span class="icon iconfont" :class="index%2==0?'icon-shijian21':'icon-shijian21'"></span>
								<span class="label">{{index%2==0?'发布时间：':'发布时间：'}}</span>
								<span class="text">{{index%2==0?item.addtime :item.addtime }}</span>
							</div>
							<div class="more_btn">
								<span class="text">{{index%2==0?'查看更多':'查看更多'}}</span>
								<span class="icon iconfont" :class="index%2==0?'icon-jiantou25':'icon-jiantou25'"></span>
							</div>
						</div>
					</div>
				</div>
			</div>
		
			<el-pagination
				background
				id="pagination" class="pagination"
				:pager-count="7"
				:page-size="pageSize"
				:page-sizes="pageSizes"
				prev-text="<"
				next-text=">"
				:hide-on-single-page="false"
				:layout='["total","prev","pager","next"].join()'
				:total="total"
				@current-change="curChange"
				@prev-click="prevClick"
				@next-click="nextClick"
				></el-pagination>

			<!-- 热门信息 -->
			<div class="hot">
				<div class="hot-title">热门信息</div>
				<div class="hot-list">
					<div class="hot-item" v-for="item in hotList" :key="item.id" @click="toNewsDetail(item)">
						<img :src="baseUrl + item.picture" alt="">
						<div class="hot-name">{{ item.title }}</div>
						<div class="hot-time">{{item.addtime}}</div>
					</div>
				</div>
			</div>
			<!-- 最新动态 -->
			<div class="news">
				<div class="news-title">最新动态</div>
				<div class="news-list">
					<div class="news-item" v-for="item in recommendList" :key="item.id" @click="toNewsDetail(item)">
						<img :src="baseUrl + item.picture" alt="">
						<div class="news-name">{{ item.title }}</div>
						<div class="news-time">{{item.addtime}}</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
	export default {
		//数据集合
		data() {
			return {
				baseUrl: this.$config.baseUrl,
				breadcrumbItem: [
				  {
					name: '公告信息'
				  }
				],
				newsList: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				layouts: '',
				title: '',
				categoryIndex: 0,
				categoryList: [],
				hotList: [],
				recommendList: [],
			}
		},
		created() {
			this.getCategoryList()
			
			this.getHotList()
			this.getRecommendList()
		},
		watch:{
			$route(newValue){
				this.getCategoryList()
			}
		},
		//方法集合
		methods: {
			getCategoryList(){
				this.$http.get('newstype/list', {}).then(res => {
					if (res.data.code == 0) {
						this.categoryList = res.data.data.list;
						if(this.$route.query.homeFenlei){
							for(let i=0;i<this.categoryList.length;i++) {
								if(this.$route.query.homeFenlei == this.categoryList[i].typename) {
									this.categoryIndex = i + 1;
									const currentRoute = this.$route;
									const routeWithoutQuery = { ...currentRoute };
									delete routeWithoutQuery.query;
									this.$router.replace(routeWithoutQuery)
									break;
								}
							}
						}
						this.getNewsList(1);
					}
				});
			},
			categoryClick(index) {
				this.categoryIndex = index
				this.getNewsList()
			},
			getNewsList(page) {
				let params = {page, limit: this.pageSize,sort:'addtime',order:'desc'};
				let searchWhere = {};
				if(this.title != '') searchWhere.title = '%' + this.title + '%';
				if(this.categoryIndex!=0){
					searchWhere.typename = this.categoryList[this.categoryIndex - 1].typename
				}
				this.$http.get('news/list', {params: Object.assign(params, searchWhere)}).then(res => {
					if (res.data.code == 0) {
						this.newsList = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			getHotList(){
				let params = {page:1, limit: 4,sort:'addtime',order:'desc'};
				this.$http.get('news/autoSort', {params: params}).then(res => {
					if (res.data.code == 0) {
						this.hotList = res.data.data.list;
					}
				});
			},
			getRecommendList(){
				let url = 'news/autoSort'
				if(localStorage.getItem('frontToken')){
					url = 'news/autoSort2'
				}
				let params = {page:1, limit: 4,sort:'addtime',order:'desc'};
				this.$http.get(url, {params: params}).then(res => {
					if (res.data.code == 0) {
						this.recommendList = res.data.data.list;
					}
				});
			},
			curChange(page) {
				this.getNewsList(page);
			},
			prevClick(page) {
				this.getNewsList(page);
			},
			nextClick(page) {
				this.getNewsList(page);
			},
			toNewsDetail(item) {
				this.$router.push({path: '/index/newsDetail', query: {id: item.id}});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.news-preview-pv {
				padding: 0 16%;
				margin: 10px auto;
				background: #fff;
				display: flex;
				width: 100%;
				position: relative;
				flex-wrap: wrap;
				.list-form-pv {
						border-radius: 10px;
						padding: 20px 10px 10px;
						box-shadow: 0 4px 8px rgba(0,0,0,.3);
						margin: -10px auto 0;
						background: #fff;
						display: flex;
						width: 100%;
						align-items: center;
						flex-wrap: wrap;
						height: auto;
						order: 2;
						.search-item {
								margin: 0 10px;
								.el-input {
										width: 100%;
									}
				.el-input /deep/ .el-input__inner {
										border: 1px solid #9E9E9E;
										border-radius: 8px;
										padding: 0 10px;
										margin: 0;
										outline: none;
										color: #333;
										width: 100%;
										font-size: 14px;
										line-height: 42px;
										height: 42px;
									}
			}
			.search-btn {
								cursor: pointer;
								border: 0;
								padding: 0px 25px;
								margin: 0 10px 0 0;
								color: #fff;
								font-weight: bold;
								font-size: 15px;
								line-height: 42px;
								border-radius: 4px;
								outline: none;
								background: #0AB8C1;
								width: auto;
								height: 42px;
								.icon {
										margin: 0 10px 0 0;
										color: #fff;
										display: none;
										font-size: 14px;
									}
			}
		}
		.category-list {
						border-radius: 10px;
						padding: 10px;
						margin: 0 auto;
						z-index: 1;
						background: #70CBD0;
						display: flex;
						width: 100%;
						position: relative;
						height: auto;
						order: 1;
						.item {
								cursor: pointer;
								border-radius: 5px;
								padding: 8px 25px;
								margin: 0 10px 0 0;
								color: #fff;
								background: none;
								display: flex;
								align-items: center;
							}
			
			.item:hover {
								color: #0AB8C1;
								background: #fff;
							}
			
			.item.active {
								color: #0AB8C1;
								background: #fff;
							}
		}
		.list11 {
						margin: 20px 0 20px auto;
						overflow: hidden;
						width: calc(86% - 20px);
						clear: both;
						order: 3;
						.list-item1 {
								padding: 0 0 20px;
								margin: 20px 0;
								display: flex;
								width: 100%;
								border-color: #D8D8D8;
								border-width: 0 0 2px;
								align-items: center;
								border-style: solid;
								height: auto;
								.img {
										overflow: hidden;
										width: 30%;
										height: 150px;
										img {
												object-fit: cover;
												width: 100%;
												transition: all 0.6s;
												height: 100%;
											}
				}
				.infoBox {
										padding: 20px;
										display: flex;
										width: 70%;
										flex-wrap: wrap;
										height: 100%;
										.name {
												border: 0px solid #eee;
												padding: 5px;
												color: #333;
												background: #fff;
												font-weight: normal;
												width: 100%;
												font-size: 22px;
												line-height: 28px;
												position: relative;
												order: 1;
											}
					.name::after {
												left: 0;
												background: #f00;
												bottom: 0px;
												display: none;
												width: 30px;
												position: absolute;
												content: "";
												transition: all 0.5s ease;
												height: 4px;
											}
					.desc {
												margin: 10px 0 0;
												overflow: hidden;
												color: #6C6C6C;
												display: -webkit-box;
												font-size: 14px;
												line-height: 24px;
												text-overflow: ellipsis;
												-webkit-box-orient: vertical;
												-webkit-line-clamp: 2;
												order: 2;
											}
					.infoCenter {
												padding: 0px 0;
												margin: 10px 0 0;
												display: flex;
												width: 50%;
												justify-content: flex-end;
												order: 4;
												.publisher_item {
														padding: 0;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.like_item {
														padding: 0;
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.collect_item {
														padding: 0;
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.view_item {
														padding: 0;
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
					}
					.infoBottom {
												margin: 10px 0 0;
												display: flex;
												width: 50%;
												justify-content: space-between;
												align-items: center;
												flex-wrap: wrap;
												order: 3;
												.time_item {
														padding: 0;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.more_btn {
														border: 1px solid #eee;
														border-radius: 20px;
														padding: 10px;
														display: none;
														width: 150px;
														text-align: center;
														.text {
																color: inherit;
															}
							.icon {
																color: inherit;
															}
						}
					}
				}
			}
			.list-item1:hover {
								cursor: pointer;
								background: none;
								.img {
					img {
												transform: scale(1.05);
											}
				}
				.infoBox {
										.name {
												border: 0px solid #ffffff50;
												color: #0AB8C1;
												font-weight: bold;
											}
					.name::after {
												width: 60px;
											}
					.desc {
												color: inherit;
											}
					.infoCenter {
						.publisher_item {
							.icon {
																color: inherit;
															}
							.label {
																color: inherit;
															}
							.text {
																color: inherit;
															}
						}
						.like_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
						.collect_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
						.view_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
					}
					.infoBottom {
						.time_item {
							.icon {
																color: inherit;
															}
							.label {
																color: inherit;
															}
							.text {
																color: inherit;
															}
						}
						.more_btn {
														background: #ffffff30;
														.text {
															}
							.icon {
															}
						}
					}
				}
			}
			.list-item2 {
								padding: 0 0 20px;
								margin: 20px 0;
								display: flex;
								width: 100%;
								border-color: #D8D8D8;
								border-width: 0 0 2px;
								align-items: center;
								border-style: solid;
								height: auto;
								.img {
										overflow: hidden;
										width: 30%;
										height: 150px;
										order: 2;
										img {
												object-fit: cover;
												width: 100%;
												transition: all 0.6s;
												height: 100%;
											}
				}
				.infoBox {
										padding: 20px;
										display: flex;
										width: 70%;
										flex-wrap: wrap;
										height: 100%;
										.name {
												border: 0px solid #eee;
												padding: 5px;
												color: #333;
												background: #fff;
												font-weight: normal;
												width: 100%;
												font-size: 22px;
												line-height: 28px;
												position: relative;
												order: 1;
											}
					.name::after {
												left: 0;
												background: #f00;
												bottom: 0;
												display: none;
												width: 30px;
												position: absolute;
												content: "";
												transition: all 0.5s ease;
												height: 4px;
											}
					.desc {
												margin: 10px 0 0;
												overflow: hidden;
												color: #6C6C6C;
												display: -webkit-box;
												font-size: 14px;
												line-height: 24px;
												text-overflow: ellipsis;
												-webkit-box-orient: vertical;
												-webkit-line-clamp: 2;
												order: 2;
											}
					.infoCenter {
												padding: 0px 0;
												margin: 10px 0 0;
												display: flex;
												width: 50%;
												justify-content: flex-end;
												order: 4;
												.publisher_item {
														padding: 0;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.like_item {
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.collect_item {
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.view_item {
														display: none;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
					}
					.infoBottom {
												margin: 10px 0 0;
												display: flex;
												width: 50%;
												justify-content: space-between;
												align-items: center;
												flex-wrap: wrap;
												order: 3;
												.time_item {
														padding: 0;
														.icon {
																margin: 0 2px 0 0;
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.label {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
							.text {
																color: #666;
																font-size: 12px;
																line-height: 1.5;
															}
						}
						.more_btn {
														border: 1px solid #eee;
														border-radius: 20px;
														padding: 10px;
														display: none;
														width: 150px;
														text-align: center;
														.text {
																color: inherit;
															}
							.icon {
																color: inherit;
															}
						}
					}
				}
			}
			.list-item2:hover {
								cursor: pointer;
								background: none;
								.img {
					img {
												transform: scale(1.05);
											}
				}
				.infoBox {
										.name {
												border: 0px solid #ffffff50;
												color: #0AB8C1;
												font-weight: bold;
											}
					.name::after {
												width: 60px;
											}
					.desc {
												color: inherit;
											}
					.infoCenter {
						.publisher_item {
							.icon {
																color: inherit;
															}
							.label {
																color: inherit;
															}
							.text {
																color: inherit;
															}
						}
						.like_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
						.collect_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
						.view_item {
							.icon {
																color: #fff;
															}
							.label {
																color: #fff;
															}
							.text {
																color: #fff;
															}
						}
					}
					.infoBottom {
						.time_item {
							.icon {
																color: inherit;
															}
							.label {
																color: inherit;
															}
							.text {
																color: inherit;
															}
						}
						.more_btn {
														background: #ffffff30;
														.text {
															}
							.icon {
															}
						}
					}
				}
			}
		}
		.hot {
						box-shadow: 0 0px 0px rgba(0,0,0,.1);
						margin: 40px 0 0;
						background: #F2F3F7;
						width: 14%;
						height: auto;
						order: 2;
						.hot-title {
								color: #fff;
								background: #0AB8C1;
								font-weight: bold;
								width: 100%;
								font-size: 22px;
								line-height: 60px;
								text-align: center;
							}
			.hot-list {
								padding: 0 0 5px 0;
								background: none;
								width: 100%;
								height: auto;
								.hot-item {
										padding: 10px 15px;
										background: none;
										width: 100%;
										height: auto;
										img {
												object-fit: cover;
												display: block;
												width: 100%;
												height: 120px;
											}
					.hot-name {
												padding: 4px 5px 0;
												color: #000;
												font-size: 18px;
												line-height: 1.5;
											}
					.hot-time {
												padding: 0 5px;
												color: #999;
												font-size: 12px;
												line-height: 1.5;
												text-align: right;
											}
				}
			}
		}
		.news {
						box-shadow: 0 0px 0px rgba(0,0,0,.1);
						top: 0;
						background: #F2F3F7;
						width: 14%;
						position: absolute;
						right: 1%;
						height: auto;
						.news-title {
								color: #fff;
								background: #0AB8C1;
								font-weight: bold;
								width: 100%;
								font-size: 22px;
								line-height: 60px;
								text-align: center;
							}
			.news-list {
								padding: 0 0 5px 0;
								background: none;
								width: 100%;
								height: auto;
								.news-item {
										padding: 10px 15px;
										background: none;
										width: 100%;
										height: auto;
										img {
												object-fit: cover;
												display: block;
												width: 100%;
												height: 120px;
											}
					.news-name {
												padding: 4px 5px 0;
												color: #000;
												font-size: 18px;
												line-height: 1.5;
											}
					.news-time {
												padding: 0 5px;
												color: #999;
												font-size: 12px;
												line-height: 1.5;
												text-align: right;
											}
				}
			}
		}
	}
	
	.index-pv1 .animation-box {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
		z-index: initial;
	}
	
	.index-pv1 .animation-box:hover {
				transform: rotate(0deg) scale(1.2) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
				z-index: 1;
	}
	
	.index-pv1 .animation-box img {
		transform: rotate(0deg) scale(1) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
	}
	
	.index-pv1 .animation-box img:hover {
				transform: rotate(0deg) scale(0.8) skew(0deg, 0deg) translate3d(0px, 0px, 0px);
				-webkit-perspective: 1000px;
				perspective: 1000px;
				transition: 0.3s;
			}
</style>
