<!-- 设置 -->
<template>
    <div class="setting">
        <div class="leftCol">
            <div class="settingsMainHeader">
                <ul class="setting-aside">                                  <!--activeColor生效在activeName==item.name  点击哪个name就是那个-->
                   <li v-for="(item,index) in settingList" :key="index" :class="{activeColor: activeName==item.name}"
                   @click="handleChlick(item)">
                      {{item.name}}
                   </li>
                </ul>
            </div>
        </div>
        <div class="contentCol">
            <!-- 标签是Vue框架自定义的标签，它的用途就是可以动态绑定我们的组件，根据数据的不同更换不同的组件 -->
            <component :is="componentSrc"></component>
        </div>
    </div>
</template>
<script>
import Info from '../components/Info';
import Upload from '../components/Upload';
export default {
    name:'setting',
    components:{
        Info,
        Upload
    },
    data() {
        return {
            settingList:[
                {name:'个人信息',path:'Info'},
                {name:'修改头像',path:'Upload'}
            ],
            componentSrc:'Info',
            activeName:'个人信息',
        }
    },
    methods: {
        handleChlick(item){
            this.componentSrc=item.path
            this.activeName=item.name
        }
    },
    name:'setting'
}
</script>

<style lang="scss" scoped>
@import '../assets/css/setting.scss'
</style>