import Vue from 'vue';
//配置路由
import VueRouter from 'vue-router'
Vue.use(VueRouter);
//1.创建组件
import Index from '@/views/index'
import Home from '@/views/home'
import Login from '@/views/login'
import NotFound from '@/views/404'
import UpdatePassword from '@/views/update-password'
import pay from '@/views/pay'
import register from '@/views/register'
import center from '@/views/center'
	import news from '@/views/modules/news/list'
	import qiyexinxi from '@/views/modules/qiyexinxi/list'
	import qiuzhijianli from '@/views/modules/qiuzhijianli/list'
	import discusszhaopinxinxi from '@/views/modules/discusszhaopinxinxi/list'
	import forum from '@/views/modules/forum/list'
	import zhaopinxinxi from '@/views/modules/zhaopinxinxi/list'
	import forumtype from '@/views/modules/forumtype/list'
	import yonghu from '@/views/modules/yonghu/list'
	import yingyaozhaopin from '@/views/modules/yingyaozhaopin/list'
	import qiyexuanjiang from '@/views/modules/qiyexuanjiang/list'
	import zhiweileixing from '@/views/modules/zhiweileixing/list'
	import forumreport from '@/views/modules/forumreport/list'
	import qiuzhixinxi from '@/views/modules/qiuzhixinxi/list'
	import messages from '@/views/modules/messages/list'
	import zhaopinjieguo from '@/views/modules/zhaopinjieguo/list'
	import config from '@/views/modules/config/list'
	import newstype from '@/views/modules/newstype/list'


//2.配置路由   注意：名字
export const routes = [{
	path: '/',
	name: '系统首页',
	component: Index,
	children: [{
		// 这里不设置值，是把main作为默认页面
		path: '/',
		name: '系统首页',
		component: Home,
		meta: {icon:'', title:'center', affix: true}
	}, {
		path: '/updatePassword',
		name: '修改密码',
		component: UpdatePassword,
		meta: {icon:'', title:'updatePassword'}
	}, {
		path: '/pay',
		name: '支付',
		component: pay,
		meta: {icon:'', title:'pay'}
	}, {
		path: '/center',
		name: '个人信息',
		component: center,
		meta: {icon:'', title:'center'}
	}
	,{
		path: '/news',
		name: '公告信息',
		component: news
	}
	,{
		path: '/qiyexinxi',
		name: '企业信息',
		component: qiyexinxi
	}
	,{
		path: '/qiuzhijianli',
		name: '求职简历',
		component: qiuzhijianli
	}
	,{
		path: '/discusszhaopinxinxi',
		name: '招聘信息评论',
		component: discusszhaopinxinxi
	}
	,{
		path: '/forum',
		name: '交流论坛',
		component: forum
	}
	,{
		path: '/zhaopinxinxi',
		name: '招聘信息',
		component: zhaopinxinxi
	}
	,{
		path: '/forumtype',
		name: '论坛分类',
		component: forumtype
	}
	,{
		path: '/yonghu',
		name: '用户',
		component: yonghu
	}
	,{
		path: '/yingyaozhaopin',
		name: '应邀招聘',
		component: yingyaozhaopin
	}
	,{
		path: '/qiyexuanjiang',
		name: '企业宣讲',
		component: qiyexuanjiang
	}
	,{
		path: '/zhiweileixing',
		name: '职位类型',
		component: zhiweileixing
	}
	,{
		path: '/forumreport',
		name: '举报记录',
		component: forumreport
	}
	,{
		path: '/qiuzhixinxi',
		name: '求职信息',
		component: qiuzhixinxi
	}
	,{
		path: '/messages',
		name: '留言反馈',
		component: messages
	}
	,{
		path: '/zhaopinjieguo',
		name: '招聘结果',
		component: zhaopinjieguo
	}
	,{
		path: '/config',
		name: '轮播图管理',
		component: config
	}
	,{
		path: '/newstype',
		name: '公告信息分类',
		component: newstype
	}
	]
	},
	{
		path: '/login',
		name: 'login',
		component: Login,
		meta: {icon:'', title:'login'}
	},
	{
		path: '/register',
		name: 'register',
		component: register,
		meta: {icon:'', title:'register'}
	},
	{
		path: '*',
		component: NotFound
	}
]
//3.实例化VueRouter  注意：名字
const router = new VueRouter({
	mode: 'hash',
	/*hash模式改为history*/
	routes // （缩写）相当于 routes: routes
})
const originalPush = VueRouter.prototype.push
//修改原型对象中的push方法
VueRouter.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
export default router;
