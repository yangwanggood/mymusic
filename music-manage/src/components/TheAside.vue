<template>
    <div class="sidebar">
        <!-- el-menu 有很多的属性 -->
        <!-- default-active 指跟没有被点击的状态的颜色 是不一样的 -->
        <!--  :collapse="collapse" data属性里面有写 -->
        <!--  active-text-color 被点击后的颜色 -->
        <!-- router 表示点击之后会进入那个页面  data里面index指向那个就转向那个 -->
        <el-menu
           class="sidebar-el-menu"
           default-active="Info" 
           :collapse="collapse" 
           background-color="#334256"
           text-color="#ffffff"
           active-text-color="#20a0ff"
           router
           >
           <template v-for="item in items">
            <template>
                <el-menu-item :index="item.index" :key="item.index">
                    <i :class="item.icon"></i>
                    {{item.title}}
                </el-menu-item>
            </template>
          
        </template>
        
        </el-menu>

   
    </div>
</template>
<script>
import bus from "../assets/js/bus"
export default {
    data() {
        return {
            //菜单折叠属性为  
            collapse:false,
            //菜单内容
            items:[
              {  
                 //图标
                  icon:'el-icon-document',
                  index:'Info',
                  title:'系统首页'
              },
              {  
                 //图标
                  icon:'el-icon-document',
                  index:'Consumer',
                  title:'用户管理'
              },
              {  
                 //图标
                  icon:'el-icon-document',
                  index:'Singer',
                  title:'歌手管理'
              },
              {  
                 //图标
                  icon:'el-icon-document',
                  index:'SongList',
                  title:'歌单管理'
              },   
            ]
        }
    },
    computed:{
        onRoutes(){
            return this.$route.path.replace('/',''); //对应的items index里面的路劲 要去掉/
        }
    },
    created(){
        // $on 接收
        bus.$on('collapse',msg=>{
            this.collapse=msg
        })
    }
}
</script>

<style scoped>
.sidebar{
    /* 显示为块状 */
    display: block;
     /* 绝对定位     */
    position: absolute;
    /* 放在最左边 */
    left: 0;
    /* 离上边70px */
    top: 70px;
    /* 下边 */
    bottom: 0px;
    background-color: #334256;
    /* 超出长的范围 就上下滚动 */
    overflow-y: scroll;
}
/* ::-webkit-scrollbar 整个滚动条 */
.sidebar::-webkit-scrollbar{
    width:0
}
/* .el-menu--collapse  表示el-menu中的collapse为false的时候 下面的方法才起效果 */
.sidebar-el-menu:not(.el-menu--collapse){
    width: 150px;
}
.sidebar >ul{
    height: 100%;
}
</style>