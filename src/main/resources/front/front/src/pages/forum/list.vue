<template>
	<div class="forum-preview">
		<div class="forum-title">
			<div>交流论坛</div>
		</div>
		<el-form :inline="true" :model="formSearch" class="list-form-pv">
			<el-form-item class="search-item">
				<el-input v-model="title" placeholder="标题"></el-input>
			</el-form-item>
			<div class="search-btn-item">
				<el-button class="searchBtn" type="primary" @click="getForumList(1)">
					<span class="icon iconfont icon-shouye-zhihui"></span>
					查询
				</el-button>
				<el-button class="pubBtn" type="primary" @click="toForumAdd">
					<span class="icon iconfont icon-shouye-zhihui"></span>
					发布帖子
				</el-button>
			</div>
		</el-form>
		<div class="category-list">
			<div class="item" @click="categoryClick(0)" :class="categoryIndex == 0 ? 'active' : ''">全部</div>
			<div v-for="(item,index) in categoryList" @click="categoryClick(index+1)" :key="index" class="item" :class="categoryIndex == index+1 ? 'active' : ''">{{item.typename}}</div>
		</div>
		<div class="z-box">
			<div class="section-content" v-for="item in forumList" :key="item.id" @click.stop="toForumDetail(item)">
				<img class="item-img" :src="$config.baseUrl + item.cover.split(',')[0]" @click.stop="imgPreView($config.baseUrl + item.cover.split(',')[0])" />
				<div class="item-title">{{item.title}}</div>
				<div class="item-user">发布人：{{item.isanon==1&&item.userid!=userid?'匿名':item.username}}</div>
				<div class="item-time">{{item.addtime}}</div>
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
		<el-dialog title="预览图" :visible.sync="previewVisible" width="50%">
			<img :src="previewImg" alt="" style="width: 100%;">
		</el-dialog>
	</div>
</template>

<script>
	export default {
		//数据集合
		data() {
			return {
				formSearch: {},
				title: '',
				layouts: '',
				forumList: [],
				total: 1,
				pageSize: 10,
				pageSizes: [],
				totalPage: 1,
				previewImg: '',
				previewVisible: false,
				userid: localStorage.getItem('frontUserid'),
				categoryIndex: 0,
				categoryList: [],
			}
		},
		created() {
			this.getCategoryList()
		},
		watch:{
			$route(newValue){
				this.getCategoryList()
			}
		},
		//方法集合
		methods: {
			getCategoryList(){
				this.$http.get('forumtype/list', {}).then(res => {
					if (res.data.code == 0) {
						this.categoryList = res.data.data.list
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
						this.getForumList(1);
					}
				});
			},
			categoryClick(index) {
				this.categoryIndex = index
				this.getForumList()
			},
			imgPreView(url){
				this.previewImg = url
				this.previewVisible = true
			},
			getForumList(page) {
				let params = {page, limit: this.pageSize, isdone: '开放', sort: 'istop,toptime', order: 'desc,desc',delflag: 0};
				let searchWhere = {};
				if(this.title != '') searchWhere.title = '%' + this.title + '%';
				if(this.categoryIndex!=0){
					searchWhere.typename = this.categoryList[this.categoryIndex - 1].typename
				}
				this.$http.get('forum/flist', {params: Object.assign(params, searchWhere)}).then(res => {
					if (res.data.code == 0) {
						this.forumList = res.data.data.list;
						this.total = res.data.data.total;
						this.pageSize = Number(res.data.data.pageSize);
						this.totalPage = res.data.data.totalPage;
						if(this.pageSizes.length==0){
							this.pageSizes = [this.pageSize, this.pageSize*2, this.pageSize*3, this.pageSize*5];
						}
					}
				});
			},
			curChange(page) {
				this.getForumList(page);
			},
			prevClick(page) {
				this.getForumList(page);
			},
			nextClick(page) {
				this.getForumList(page);
			},
			toForumAdd() {
				this.$router.push('/index/forumAdd');
			},
			toForumDetail(item) {
				this.$router.push({path: '/index/forumDetail', query: {id: item.id}});
			}
		}
	}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.forum-preview {
				padding: 0;
				margin: 10px auto;
				background: #fff;
				display: flex;
				width: 100%;
				position: relative;
				flex-wrap: wrap;
				.forum-title {
						margin: 0;
						color: #fff;
						background: url(http://codegen.caihongy.cn/20241119/91b3da33f957476e8c833cb4ebc67d27.png) center center/cover no-repeat ;
						width: 100%;
						font-size: 30px;
						line-height: 160px;
						text-align: center;
						height: 240px;
						order: 1;
						div {
								padding: 0 10px;
								color: inherit;
								width: 100%;
								font-size: inherit;
								text-align: center;
								height: 54px;
							}
		}
		.list-form-pv {
						border-radius: 10px;
						padding: 20px 10px 10px;
						box-shadow: 0 4px 8px rgba(0,0,0,.3);
						margin: -16px auto 10px;
						background: #fff;
						display: flex;
						width: 68%;
						align-items: center;
						flex-wrap: wrap;
						height: auto;
						order: 3;
						.search-item {
								margin: 0 10px;
								/deep/ .el-form-item__content {
										display: flex;
										align-items: center;
									}
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
			.search-btn-item {
								display: flex;
								.searchBtn {
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
											}
				}
				
				.searchBtn:hover {
										background: #0AB8C190;
									}
				
				.pubBtn {
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
				
				.pubBtn:hover {
										background: #0AB8C190;
									}
			}
		}
		.category-list {
						border-radius: 10px;
						padding: 10px;
						margin: -60px auto 0;
						z-index: 1;
						background: #70CBD0;
						display: flex;
						width: 68%;
						position: relative;
						height: 56px;
						order: 2;
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
		.z-box {
						padding: 20px 0;
						margin: 0px auto;
						width: 68%;
						order: 4;
						.section-content {
								cursor: pointer;
								padding: 20px;
								margin: 0 0 30px;
								background: #F7F9F9;
								display: flex;
								width: 100%;
								border-color: #0AB8C1;
								border-width: 15px 0 0;
								align-items: center;
								border-style: solid;
								flex-wrap: wrap;
								.item-img {
										border-radius: 0px;
										object-fit: cover;
										width: 80px;
										transition: all .4s;
										height: 80px;
									}
				.item-title {
										margin: 0 0 0 20px;
										color: #333;
										width: calc(100% - 100px);
										font-size: 16px;
									}
				.item-user {
										margin: 20px 0 0;
										color: #666;
										width: 50%;
										font-size: 14px;
									}
				.item-time {
										color: #999;
										width: 50%;
										font-size: 14px;
										text-align: right;
									}
			}
			.section-content:hover {
								background: #0AB8C120;
								.item-title {
										color: #111;
									}
				.item-user {
										color: #111;
									}
				.item-time {
										color: #999;
									}
			}
		}
	}
</style>
