<template>
    <div>
        <loginLogo/>
        <div class="signUp">
            <div class="signUp-head">
               <span>账号登录</span> 
            </div>
            <!-- label-width="70px" 左边文字宽度 -->
            <el-form :model="loginForm" ref="loginForm" label-width="70px" class="demo-ruleForm" :rules="rules">
            <el-form-item prop="username" label="用户名">
                <el-input v-model="loginForm.username" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password" label="密码">
                <el-input type="password" v-model="loginForm.password" placeholder="请输入密码"></el-input>
            </el-form-item>
           
            <div class="login-btn">
                <!-- goback(-1)" -1 就是回到原来的界面   primary 颜色会变-->
                <el-button @click="goSignUp">注册</el-button>
                <el-button type="primary" @click="LoginIn">确定</el-button>
            </div>
            </el-form>
        </div>
    </div>
</template>
<script>
import loginLogo from '../components/LoginLogo'

import { mixin } from '../mixins'
import {loginIn} from '../api/index'
export default {
    name:'login-in',
    mixins:[mixin],
    
    components:{
        loginLogo
    },
    data() {
        return {
            loginForm:{
                username:'',   //用户名
                password:'',   //密码
                
            },
          
            rules:{
                username: [
                { required: true, trigger: 'blur',message:'请输入正确的用户名'}
            ],
               password: [
               { required: true, trigger: 'blur', message:'请输入密码' }
             ],
            }   //表单提交的规则 
        }
    },
   
    mounted(){
        this.changge('登录');
    },
    methods:{
        LoginIn(){
        let _this=this;
        //URLSearchParams new一个URLSearchParams键值对 存
        let params=new URLSearchParams();
        params.append('username',this.loginForm.username);
        params.append('password',this.loginForm.password);
        loginIn(params)
                   .then(res=>{
                    if (res.code==1) {
                        _this.notify('登录成功','success');
                        setTimeout(function(){
                            _this.$router.push({path:'/'});
                            _this.$store.commit('setLoginIn',true);
                            _this.$store.commit('setUserId',res.userMsg.id);
                            _this.$store.commit('setUsername',res.userMsg.name);
                            _this.$store.commit('setAvator',res.userMsg.avator);    
                        },2000);
                    }else{
                        _this.changge('首页');
                        _this.notify('登录失败','error');
                    }
                   }).catch(err=>{
                    this.notify('用户名或者密码错误','error');
                   })
                   
        },
        goSignUp(){
            this.changge('注册');
            this.$router.push({path:'/sign-up'})
        },
        changge(value){
            this.$store.commit('setActiveName',value)
        }
    }
}
</script>


<style lang="scss" scoped>
@import '../assets/css/sign-up.scss';
</style>