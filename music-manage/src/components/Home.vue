<template>
    <div>
       <the-header></the-header>
       <the-aside></the-aside>
       <!-- 它在assets css中 通过main.js调用了   content-collapse':collapse :如果collapse为false就不起作用  反之起作用-->
       <div class="content-box" :class="{'content-collapse':collapse}"> 
       <!-- 指的router里面的index.js里面的 -->
       <router-view></router-view> 
    </div>
    </div>
</template>


<script>
import TheHeader from './TheHeader.vue'
import TheAside from './TheAside.vue'
import bus from "../assets/js/bus"
export default {
    //导出的组件
   components:{
    TheHeader,
    TheAside
   },
   data() {
    return {
        collapse:false
    }
   },
   created(){
        // $on 接收
        bus.$on('collapse',msg=>{
            this.collapse=msg
        })
    },
}
</script>