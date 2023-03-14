<!-- 更换头像 -->
<template>
    <div class="upload">
        <div class="title">修改头像</div>
        <hr/>
        <div class="section">
            <!-- :show-file-list 是否显示文件列表-->
            <el-upload drag :action="uploadUrl()" :show-file-list="false" :on-success="handleAvatorSuccess"
             :before-upload="beforeUpload">
              <i class="el-icon-upload"></i>
                <div>将文件拖拽到此处 或<span style="color:blue">更换图片</span>
                </div>
              <div>只能上传jpg或png图片,并且不能超过10mb</div>
            </el-upload>
           
        </div>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';
import { mixin } from '../mixins'
export default {
    name:'upload',
    mixins:[mixin],

    computed:{
        ...mapGetters([
            'userId',
            'avator'
        ])
    },
    methods:{
        uploadUrl(){   //文件上传
            return `${this.$store.state.configure.HOST}/consumer/updateConsumerPic?id=${this.userId}`
        },
        handleAvatorSuccess(res,file){  //文件传输完毕后台
           if(res.code==1){
            this.$store.commit('setAvator',res.avator)
            this.notify('修改成功','success')
           }else{
            this.notify('修改失败','success')
           }
        },
        beforeUpload(file){   //上传之前的效验
           const IsJPG=file.type=='image/jpeg';
           const ISL10M=file.size/1024/1024<10   //除以两个的就是兆了
           if(!IsJPG){
            this.notify('上传文件只能是jpg或img','error')
                return false;
           }
           if(!ISL10M){
            this.notify('上传文件大小不能超过10MB','error')
                return false;
           }
           return true;
        }
    }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/upload.scss'
</style>