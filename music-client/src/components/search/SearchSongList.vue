<template>
    <div class="search-song-Lists">
        <content-list :contentList="albumDatas"></content-list>
    </div>
</template>

<script>

import ContentList from '../ContentList'
import {getlikeTitle} from '../../api/index'
import { mixin } from '../../mixins';

export default {
    mixins:[mixin],
    name:'search-song-list',
    components: { ContentList },
    data() {
        return {
            albumDatas:[]  //需要搜索内容的暂存数组
        }
    },
    mounted(){
        this.getSearchList();
    },
    methods:{
        getSearchList(){
            //keywords 在TheHeader.vue里面传过来的
            if(!this.$route.query.keywords){
                this.notify("您输入的内容为空",'warning')
            }else{
                getlikeTitle(this.$route.query.keywords)
                 .then(res=>{
                if(res){
                    this.albumDatas=res
                }else{
                    this.notify("暂无歌单内容",'warning')
                }
                  })
            }
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-song-Lists.scss'
</style>