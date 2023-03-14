<template>
    <div class="header">
        <!-- 设置一个折叠的小图标 -->
        <div class="collapse-btn" @click="collapseChange">
           <i class="el-icon-menu"></i>
        </div>
        <div class="logo">music后台管理</div>
        <div class="header-right">
            <div class="btn-fullscreen" @click="handleFullScreen"> 
                <!-- 提示  placement="bottom 提示信息放在最下边-->
                <el-tooltip :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
             <i class="el-icon-rank"></i>
                </el-tooltip>
            </div>
            <div class="user-avator">
                <img src="../assets/img/user.jpg"/>
            </div>
            <!-- trigger="click" @command="hadleCommand 当点击的时候会触发 hadleCommand command可以传递参数-->
            <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                 <span class="el-dropdown-link">
                {{userName}}
                <i class="el-icon-caret-bottom"></i>
            </span>
           
            <!-- slot="dropdown 点击 就会出现一个向下拉 框 -->
            <el-dropdown-menu slot="dropdown"> 
                <el-dropdown-item command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</template>

<script>
import bus from "../assets/js/bus"
export default {
    data() {
        return {
            collapse:false,
            fullscreen:false,
            
        }
    },
    computed:{ 
       userName(){
        return localStorage.getItem('userName')//获取指定key本地存储的值
       }
    },
    methods:{
        //侧边栏折叠
        collapseChange(){
            this.collapse=!this.collapse;
            //发送
            bus.$emit('collapse',this.collapse);
        },
        //全屏事件
        handleFullScreen(){
            // 如果是fullscreen为真 就exit关
                if(this.fullscreen){
                    if(document.exitFullscreen){
                        document.exitFullscreen();                //各个浏览器支持的方式 
                    }else if(document.webkitCancelFullScreen){    //sa fari Chrome
                        document.webkitCancelFullScreen();
                    }else if(document.mozCancelFullScreen){       //firefox
                        document.mozCancelFullScreen();
                    }else if(document.msExitFullScreen){         //ie
                        document.msExitFullScreen();
                    }
                }else{                                       //非全屏状态到 全屏状态
                         let element =document.documentElement;  //整个页面元素
                         if(element.requestFullscreen){
                            element.requestFullscreen();
                         }else if(element.webkitCancelFullScreen){
                            element.webkitCancelFullScreen();
                         }else if(element.mozCancelFullScreen){
                            element.mozCancelFullScreen();
                         }else if(element.msExitFullScreen){
                            element.msExitFullScreen();
                         }
                    }
                  this.fullscreen=!this.fullscreen;
        },
        handleCommand(command){
           if(command=="logout"){
            //localStorage.removeItem(this.userName); 移除掉
            localStorage.removeItem(this.userName);
            this.$router.push("/")
           }
        }
    }
}
</script>

<style scoped>
.header{
    /*相对定位 */
    position: relative;
    background-color: #253041;
    /* 带阴影的颜色 更好看 */
    box-sizing: border-box;
    height: 70px;
    width: 100%;
    font-size: 22px;
    /* 字的颜色 */
    color: #ffffff;
}
.collapse-btn{
    /* 放在左边 */
    float: left;
    /*  左右间距   上下间距 */
    padding:0 21px;
    /* 移动过来 就像超链接的效果一样 */
    cursor: pointer;
    /* 行高 */
    line-height: 70px;
}
.header .logo{
    float: left;
    line-height: 70px;
}
.header-right{
    float: right;
    /* 右边距 */
    padding-right: 50px;
    /* 弹性盒子 */
    display: flex;
    height: 70px;
    align-items: center;

}
.btn-fullscreen{
    /* 旋转45% */
    transform: rotate(45deg);
    /* 距离 旁边的 div 5px */
    margin-right: 5px;
    font-size: 24px;
}
.user-avator{
    /* 距离左边的div 20px */
    margin-left: 20px;
}
.user-avator img{
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
}
.user-name{
    margin-left: 10px;
}
.el-dropdown-link{
    color: #ffffff;
    /* 鼠标过来的时候会 */
    cursor: pointer;
}

</style>