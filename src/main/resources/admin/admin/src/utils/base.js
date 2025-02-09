const base = {
    get() {
        return {
            url : "http://localhost:8080/hire/",
            name: "hire",
            // 退出到首页链接
            indexUrl: 'http://localhost:8080/hire/front/dist/index.html'
        };
    },
    getProjectName(){
        return {
            projectName: "基于SpringBoot的校园招聘推荐系统"
        } 
    }
}
export default base
