<template>
	<div class="menu-preview">
		<!-- 竖向-2 -->
		<el-scrollbar :wrap-class="isCollapse ? 'scrollbar-wrapper scrollbar-wrapper-close' : 'scrollbar-wrapper scrollbar-wrapper-open'">
			<el-button :style="verticalStyle2[isCollapse?'close':'open'].btn.default" type="primary" @click="collapse">
				<span class="icon iconfont"
					:style="verticalStyle2[isCollapse?'close':'open'].btn.icon.default"
					:class="verticalStyle2[isCollapse?'close':'open'].btn.icon.text"></span>{{verticalStyle2[isCollapse?'close':'open'].btn.text}}
			</el-button>
			<div class="title" :style="verticalStyle2[isCollapse?'close':'open'].title.box.default">
				<el-image v-if="verticalStyle2[isCollapse?'close':'open'].title.img.flag" :style="verticalStyle2[isCollapse?'close':'open'].title.img.default" :src="verticalStyle2[isCollapse?'close':'open'].title.img.url" fit="cover"></el-image>
				<span class="title-name" :style="verticalStyle2[isCollapse?'close':'open'].title.text.default">{{verticalStyle2[isCollapse?'close':'open'].title.text.showType?verticalStyle2[isCollapse?'close':'open'].title.text.text:''}} {{this.$project.projectName}}</span>
			</div>
			<div class="userinfo"
				:style="verticalStyle2[isCollapse?'close':'open'].userinfo.box.default">
				<el-image v-if="avatar" :style="verticalStyle2[isCollapse?'close':'open'].userinfo.img.default" :src="avatar?this.$base.url + avatar:require('@/assets/img/avator.png')" fit="cover"></el-image>
				<div :style="verticalStyle2[isCollapse?'close':'open'].userinfo.nickname.default">
				{{this.$storage.get('adminName')}}</div>
			</div>
			<el-menu :default-active="activeMenu" :unique-opened="true" :style="verticalStyle2[isCollapse?'close':'open'].menu.box.default"
				class="el-menu-vertical-2" :collapse-transition="false" :collapse="isCollapse">
				<el-menu-item class="home" :popper-append-to-body="false" popper-class="home" @click.native="menuHandler('')" :style="verticalStyle2[isCollapse?'close':'open'].home.one.box.default" index="/">
					<div class="el-tooltip">
						<i :style="verticalStyle2[isCollapse?'close':'open'].home.one.icon.default"
							class="icon iconfont icon-shouye-zhihui"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].home.one.title.default"
							slot="title">{{verticalStyle2.open.home.one.title.text}}</span>
					</div>
				</el-menu-item>
				<el-submenu class="user" popper-class="user" :popper-append-to-body="false"
					:style="verticalStyle2[isCollapse?'close':'open'].user.one.box.default" index="1">
					<template slot="title">
						<i :style="verticalStyle2[isCollapse?'close':'open'].user.one.icon.default"
							class="icon iconfont icon-kuaijiezhifu"></i>
						<span :style="verticalStyle2[isCollapse?'close':'open'].user.one.title.default"
							slot="title">{{verticalStyle2.open.user.one.title.text}}</span>
					</template>
					<el-menu-item index="/updatePassword" @click="menuHandler('updatePassword')">修改密码</el-menu-item>
					<el-menu-item index="/center" @click="menuHandler('center')">个人信息</el-menu-item>
				</el-submenu>
				<template v-for="(menu,index) in menuList.backMenu" v-if="menu.child.length&&menu.child[0].tableName!='hasBoard'">
					<el-submenu v-if="menu.child.length > 1 || !verticalIsMultiple" class="other" popper-class="other" :popper-append-to-body="false" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" :index="index+2+''">
						<template slot="title">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{menu.menu + (verticalFlag ? '管理' : '')}}</span>
						</template>
						<el-menu-item v-for="(child,sort) in menu.child" :key="sort" :index="'/'+child.tableName" @click="menuHandler(child.tableName)">{{ child.menu }}</el-menu-item>
					</el-submenu>
					<el-menu-item v-if="menu.child.length <= 1 && verticalIsMultiple" class="other" popper-class="other" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" @click="menuHandler(menu.child[0].tableName)" :index="'/'+menu.child[0].tableName">
						<div class="el-tooltip">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{menu.child[0].menu + (verticalFlag ? '管理' : '')}}</span>
						</div>
					</el-menu-item>
				</template>
			</el-menu>
		</el-scrollbar>


	</div>
</template>

<script>
import menu from '@/utils/menu'
export default {
	data() {
		return {
			menuList: [],
			dynamicMenuRoutes: [],
			role: '',
			user: null,
			avatar:'',
			icons: [
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-fold',
				'el-icon-s-unfold',
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
				'el-icon-s-custom',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check',
				'el-icon-s-grid',
				'el-icon-menu',
				'el-icon-chat-dot-square',
				'el-icon-message',
				'el-icon-postcard',
				'el-icon-position',
				'el-icon-microphone',
				'el-icon-close-notification',
				'el-icon-bangzhu',
				'el-icon-time',
				'el-icon-odometer',
				'el-icon-crop',
				'el-icon-aim',
				'el-icon-switch-button',
				'el-icon-full-screen',
				'el-icon-copy-document',
				'el-icon-mic',
				'el-icon-stopwatch',
			],
			menulistBorderBottom: {},
			verticalFlag: false,
			isCollapse: false,
			verticalStyle2: {"isCollapse":false,"close":{"contentBox":{"hover":{},"active":{"margin":"0 0 0 54px"},"default":{"minHeight":"100%","padding":"0","margin":"0 0 0 64px","position":"relative","display":"block"}},"box":{"hover":{},"active":{"width":"54px"},"default":{"boxShadow":"1px 0 6px  rgba(64, 158, 255, .3)","overflow":"hidden","top":"0","left":"0","background":"#304156","bottom":"0","width":"64px","fontSize":"0px","position":"fixed","transition":"width 0.3s","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":true,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"44px","fontSize":"12px","color":"rgba(64, 158, 255, 1)"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"red !important","lineHeight":"56px","color":"#fff","height":"56px"},"active":{"padding":"0 20px","backgroundColor":"blue !important","lineHeight":"56px","color":"#fff","height":"56px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"56px","color":"#666","height":"56px"}},"box":{"hover":{},"default":{"border":"none"}}},"box":{"hover":{},"default":{"border":0,"padding":"0","listStyle":"none","margin":"0","position":"relative","background":"#FFF"}},"one":{"box1":{"hover":{"color":"#fff","background":"blue"},"active":{"color":"#fff","background":"blue"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#333","background":"#fff"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"14px","color":"#fff","height":"40px"},"text":"icon-xihuan"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","margin":"0 0 10px","outline":"none","color":"#fff","borderRadius":"0","background":"rgba(64, 158, 255, 1)","width":"auto","fontSize":"14px","height":"40px"},"text":"切换"},"user":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"red !important","lineHeight":"56px","color":"#fff","height":"56px"},"active":{"padding":"0 20px","backgroundColor":"blue !important","lineHeight":"56px","color":"#fff","height":"56px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"56px","color":"#656","height":"56px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#fff","background":"blue"},"active":{"color":"#fff","background":"blue"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#323","background":"#fff"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"fontSize":"24px","lineHeight":"1.5","color":"#fff","textAlign":"center"}},"img":{"hover":{},"default":{"width":"100%","objectFit":"cover","borderRadius":"20px","display":"block","height":"170px"}},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"red !important","lineHeight":"56px","color":"#fff","height":"56px"},"active":{"padding":"0 20px","backgroundColor":"blue !important","lineHeight":"56px","color":"#fff","height":"56px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"56px","color":"#646","height":"56px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#fff","background":"blue"},"active":{"color":"#fff","background":"blue"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#313","background":"#fff"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}}},"open":{"contentBox":{"hover":{},"default":{"minHeight":"100%","padding":"0px 0 0 177px","margin":"0","position":"relative","background":"#E1E1E1","display":"block"}},"box":{"hover":{},"default":{"boxShadow":"0px 0 9px  rgba(0, 0, 0, .3)","padding":"53px 0 0","bottom":"0","transition":"width 0.3s","overflow":"hidden","top":"0px","borderRadius":"0","left":"0","background":"#FFFFFF","width":"200px","fontSize":"15px","position":"fixed","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":false,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"0 10px","margin":"10px 0 0","alignItems":"center","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"1.5","fontSize":"16px","color":"#fff"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"borderColor":"#5BAAFF","color":"#5BAAFF","background":"none","borderWidth":"0 0 1px 0","lineHeight":"40px","borderStyle":"solid","height":"40px"},"active":{"borderColor":"#5BAAFF","color":"#5BAAFF","background":"none","borderWidth":"0 0 1px 0","lineHeight":"40px","borderStyle":"solid","height":"40px"},"default":{"borderColor":"#D8D8D8","margin":"0 10px","color":"#152649","borderWidth":"0 0 1px 0","width":"calc(100% - 20px)","lineHeight":"40px","fontSize":"inherit","borderStyle":"solid","transition":"all 0s","height":"40px"}},"box":{"hover":{},"default":{"border":"none","borderColor":"#88d8fb","margin":"10px","overflow":"hidden","borderRadius":"10px","borderWidth":"0px","background":"#EAFDFF","borderStyle":"solid"}}},"box":{"hover":{},"default":{"border":0,"padding":"0","listStyle":"none","margin":"0","background":"none","flexDirection":"column","display":"flex","position":"relative"}},"one":{"box1":{"hover":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"active":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"default":{"cursor":"pointer","padding":"0 10px 0 30px","whiteSpace":"nowrap","color":"#152649","background":"none","lineHeight":"68px","fontSize":"inherit","position":"relative","fontWeight":"400","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","textAlign":"center","width":"34px","fontSize":"18px","fontWeight":"400"},"flag":true},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0 auto","fontSize":"inherit","height":"auto"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"inherit"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"8px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"40px","color":"#000","height":"40px"},"text":"icon-kaiguan4"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","color":"#000","borderRadius":"4px","background":"none","display":"none","width":"auto","fontSize":"14px","height":"30px"},"text":""},"user":{"two":{"title":{"hover":{"borderColor":"#5BAAFF","color":"#5BAAFF","background":"none","borderWidth":"0 0 1px 0","lineHeight":"40px","borderStyle":"solid","height":"40px"},"active":{"borderColor":"#5BAAFF","color":"#5BAAFF","background":"none","borderWidth":"0 0 1px 0","lineHeight":"40px","borderStyle":"solid","height":"40px"},"default":{"borderColor":"#D8D8D8","color":"#152649","borderWidth":"0 0 1px 0","lineHeight":"40px","fontSize":"inherit","borderStyle":"solid","transition":"all 0s","height":"40px"}},"box":{"hover":{},"default":{"border":"none","borderColor":"#88d8fb","margin":"10px","overflow":"hidden","borderRadius":"10px","borderWidth":"0px","background":"#EAFDFF","borderStyle":"solid"}}},"one":{"box1":{"hover":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"active":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"default":{"cursor":"pointer","padding":"0 10px 0 30px","whiteSpace":"nowrap","color":"#152649","background":"none","lineHeight":"68px","fontSize":"inherit","position":"relative","fontWeight":"400","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","textAlign":"center","width":"34px","fontSize":"18px","fontWeight":"400"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0","fontSize":"inherit","height":"auto","order":"2"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"inherit"},"text":"个人中心"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"8px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"fontSize":"24px","lineHeight":"1.5","color":"#fff","textAlign":"center"}},"img":{"hover":{},"default":{"width":"100%","objectFit":"cover","borderRadius":"20px","display":"block","height":"170px"}},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"red","height":"50px"},"active":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"blue","height":"50px"},"default":{"padding":"0 40px","lineHeight":"50px","color":"#664","background":"#fff","height":"50px"}},"box":{"hover":{},"default":{"border":"none","display":"none"}}},"one":{"box1":{"hover":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"active":{"borderRadius":"10px","color":"#FFFFFF","background":"linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%)"},"default":{"cursor":"pointer","padding":"0 10px 0 30px","whiteSpace":"nowrap","color":"#152649","background":"none","lineHeight":"68px","fontSize":"inherit","position":"relative","fontWeight":"400","height":"auto"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 3px","color":"inherit","textAlign":"center","width":"34px","fontSize":"18px","fontWeight":"400"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0","fontSize":"inherit","height":"auto"}},"title":{"hover":{},"default":{"color":"inherit","verticalAlign":"middle","fontSize":"inherit"},"text":"系统首页"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"20px"}}}}}},
			verticalIsMultiple: true,
		}
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
		}
	},
	watch:{
		avatar(){
			this.$forceUpdate()
		},
	},
	mounted() {
		const menus = menu.list()
		if(menus) {
			this.menuList = menus
		} else {
			let params = {
				page: 1,
				limit: 1,
				sort: 'id',
			}
			
			this.$http({
				url: "menu/list",
				method: "get",
				params: params
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					this.menuList = JSON.parse(data.data.list[0].menujson);
					this.$storage.set("menus", this.menuList);
				}
			})
		}
		this.role = this.$storage.get('role')
		
		for(let i=0;i<this.menuList.length;i++) {
			if(this.menuList[i].roleName == this.role) {
				this.menuList = this.menuList[i];
				break;
			}
		}
		this.styleChange()
		
		let sessionTable = this.$storage.get("sessionTable")
		this.$http({
			url: sessionTable + '/session',
			method: "get"
		}).then(({
			data
		}) => {
			if (data && data.code === 0) {
				if(sessionTable == 'yonghu') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable == 'qiyexinxi') {
					this.avatar = data.data.fengmian
				}
				if(sessionTable=='users') {
					this.avatar = data.data.image
				}
				this.user = data.data;
			} else {
				let message = this.$message
				message.error(data.msg);
			}
		});
	},
	created(){
		this.icons.sort(()=>{
			return (0.5-Math.random())
		})
	},
	methods: {
		collapse() {
		  this.isCollapse = !this.isCollapse
		  this.$emit('oncollapsechange', this.isCollapse)
		},
		styleChange() {
			this.$nextTick(() => {
								document.querySelectorAll('.el-menu-vertical-demo .el-submenu .el-menu').forEach(el => {
				  el.removeAttribute('style')
				  const icon = {"border":"none","display":"none"}
				  Object.keys(icon).forEach((key) => {
					el.style[key] = icon[key]
				  })
				})
											})
		},
		menuHandler(name) {
			let router = this.$router
			name = '/'+name
			router.push(name)
		},
	}
}
</script>
<style lang="scss" scoped>
	.menu-preview {
		.el-scrollbar {
			height: 100%;
	
			& /deep/ .scrollbar-wrapper {
				overflow-x: hidden;
			}
		
			// 竖向
			.el-menu-vertical-demo {
				.el-submenu:first-of-type /deep/ .el-submenu__title .el-submenu__icon-arrow {
					display: none;
				}
			}
			
			.el-menu-vertical-demo>.el-menu-item {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo>.el-menu-item:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-submenu__title {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-submenu__title:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}
			
			.el-menu-vertical-demo .el-submenu {
				padding: 0;
				margin: 0;
				list-style: none;
			}
			
			// .el-menu-vertical-demo .el-submenu /deep/ .el-menu {
// 					// 		border: none;
// 					// 		display: none;
// 					// }
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-menu .el-menu-item {
				padding: 0 40px;
				color: #666;
				background: #fff;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-menu .el-menu-item:hover {
				padding: 0 40px;
				color: #fff;
				background: red;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu /deep/ .el-menu .el-menu-item.is-active {
				padding: 0 40px;
				color: #fff;
				background: blue;
				line-height: 50px;
				height: 50px;
			}
			// 竖向
		}
	}
	// 竖向 样式二-open
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		padding: 0 10px 0 30px;
		color: #152649;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		font-size: inherit;
		line-height: 68px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title {
		cursor: pointer !important;
		padding: 0 10px 0 30px !important;
		color: #152649 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		font-size: inherit !important;
		line-height: 68px !important;
		position: relative !important;
		height: auto !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title:hover {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active /deep/ .el-submenu__title {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title .iconfont {
		margin: 0 3px;
		color: inherit;
		font-weight: 400;
		vertical-align: middle;
		width: 34px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 8px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 /deep/ .el-submenu.other .el-menu {
		border: none;
		border-radius: 10px;
		margin: 10px;
		overflow: hidden;
		background: #EAFDFF;
		border-color: #88d8fb;
		border-width: 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item {
		margin: 0 10px !important;
		color: #152649 !important;
		width: calc(100% - 20px) !important;
		font-size: inherit !important;
		border-color: #D8D8D8 !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		transition: all 0s !important;
		height: 40px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover {
		color: #5BAAFF !important;
		background: none !important;
		border-color: #5BAAFF !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		height: 40px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active {
		color: #5BAAFF !important;
		background: none !important;
		border-color: #5BAAFF !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		height: 40px !important;
	}

	// 竖向 样式二-close
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: #fff;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		white-space: nowrap !important;
		background: #fff !important;
		position: relative !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title:hover {
		color: #fff !important;
		background: blue !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item:hover {
		background-color: red !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item.is-active {
		background-color: blue !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
	
	// 竖向 样式二-open-首页
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		padding: 0 10px 0 30px;
		color: #152649;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		font-size: inherit;
		line-height: 68px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		border-radius: 10px;
		color: #FFFFFF;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		border-radius: 10px;
		color: #FFFFFF;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title {
		cursor: pointer !important;
		padding: 0 10px 0 30px !important;
		color: #152649 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		font-size: inherit !important;
		line-height: 68px !important;
		position: relative !important;
		height: auto !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title:hover {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title .iconfont {
		margin: 0 3px;
		color: inherit;
		font-weight: 400;
		vertical-align: middle;
		width: 34px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 8px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
		border-radius: 10px;
		margin: 10px;
		overflow: hidden;
		background: #EAFDFF;
		border-color: #88d8fb;
		border-width: 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		margin: 0 10px;
		color: #152649;
		width: calc(100% - 20px);
		font-size: inherit;
		border-color: #D8D8D8;
		border-width: 0 0 1px 0;
		line-height: 40px;
		border-style: solid;
		transition: all 0s;
		height: 40px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
		color: #5BAAFF;
		background: none;
		border-color: #5BAAFF;
		border-width: 0 0 1px 0;
		line-height: 40px;
		border-style: solid;
		height: 40px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		color: #5BAAFF;
		background: none;
		border-color: #5BAAFF;
		border-width: 0 0 1px 0;
		line-height: 40px;
		border-style: solid;
		height: 40px;
	}
	
	// 竖向 样式二-close-首页
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: #fff;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: #fff;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title:hover {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item:hover {
		background-color: red !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item.is-active {
		background-color: blue !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
	
	// 竖向 样式二-open-个人中心
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		padding: 0 10px 0 30px;
		color: #152649;
		white-space: nowrap;
		background: none;
		font-weight: 400;
		font-size: inherit;
		line-height: 68px;
		position: relative;
		height: auto;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		border-radius: 10px;
		color: #FFFFFF;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		border-radius: 10px;
		color: #FFFFFF;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%);
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title {
		cursor: pointer !important;
		padding: 0 10px 0 30px !important;
		color: #152649 !important;
		white-space: nowrap !important;
		background: none !important;
		font-weight: 400 !important;
		font-size: inherit !important;
		line-height: 68px !important;
		position: relative !important;
		height: auto !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title:hover {
		border-radius: 10px !important;
		color: #FFFFFF !important;
		background: linear-gradient( 150deg, #59A8FF 0%, #8CEFFC 100%) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title .iconfont {
		margin: 0 3px;
		color: inherit;
		font-weight: 400;
		vertical-align: middle;
		width: 34px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 8px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 /deep/ .el-submenu.user .el-menu {
		border: none;
		border-radius: 10px;
		margin: 10px;
		overflow: hidden;
		background: #EAFDFF;
		border-color: #88d8fb;
		border-width: 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item {
		margin: 0 10px !important;
		color: #152649 !important;
		width: calc(100% - 20px) !important;
		font-size: inherit !important;
		border-color: #D8D8D8 !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		transition: all 0s !important;
		height: 40px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover {
		color: #5BAAFF !important;
		background: none !important;
		border-color: #5BAAFF !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		height: 40px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active {
		color: #5BAAFF !important;
		background: none !important;
		border-color: #5BAAFF !important;
		border-width: 0 0 1px 0 !important;
		line-height: 40px !important;
		border-style: solid !important;
		height: 40px !important;
	}
	
	// 竖向 样式二-close-个人中心
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: #fff;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		color: #fff;
		background: blue;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		white-space: nowrap !important;
		background: #fff !important;
		position: relative !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title:hover {
		color: #fff !important;
		background: blue !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user /deep/ .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item:hover {
		background-color: red !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item.is-active {
		background-color: blue !important;
		padding: 0 20px;
		color: #fff;
		line-height: 56px;
		height: 56px;
	}
</style>
