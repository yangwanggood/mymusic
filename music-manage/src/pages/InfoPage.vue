<template>
    <div>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="6">
                <el-card>
             <div class="grid-content">
                  <div class="grid-conton">
                     <div class="num-one">{{consumerCount}}</div>
                     <div>用户总数</div>
                  </div>
                </div>
                 </el-card>
              
            </el-col>
            <el-col :span="6">
                <el-card>
             <div class="grid-content">
                  <div class="grid-conton">
                     <div class="num-one">{{songCount}}</div>
                     <div>歌曲总数</div>
                  </div>
                </div>
                 </el-card>
              
            </el-col>
            <el-col :span="6">
                <el-card>
             <div class="grid-content">
                  <div class="grid-conton">
                     <div class="num-one">{{singerCount}}</div>
                     <div>歌手总数</div>
                  </div>
                </div>
                 </el-card>
              
            </el-col>
            <el-col :span="6">
                <el-card>
             <div class="grid-content">
                  <div class="grid-conton">
                     <div class="num-one">{{SongListCount}}</div>
                     <div>歌单数量</div>
                  </div>
                </div>
                 </el-card>
              
            </el-col>
            
        </el-row>
        <el-row :gutter="20" class="mgb20">
            <el-col :span="12">
               <h3>用户男女比例</h3>
               <div style="background-color:white">
               <ve-pie :data="consumerSex" :theme="options"></ve-pie>
            </div>
            </el-col>
            <el-col :span="12">
               <h3>歌曲风格</h3>
               <div style="background-color:white">
               <ve-histogram :data="songListStyle"></ve-histogram>
            </div>
            </el-col>
            <el-col :span="12">
               <h3>歌手类型</h3>
               <div style="background-color:white">
               <ve-pie :data="singerSex"></ve-pie>
            </div>
            </el-col>
            <el-col :span="12">
               <h3>地区划分表</h3>
               <div style="background-color:white">
               <ve-histogram :data="nationality"></ve-histogram>
            </div>
            </el-col>
        </el-row>
        
   
    </div>
</template>
<script>
import {getAllConsumer,getAllSinger,allSong,getAllSongList} from '../api/index';

export default {
   data() {
    return {
        consumerCount:0,
        songCount:0,
        singerCount:0,
        SongListCount:0,
        consumer:[],//所有用户
        song:[],//所有歌曲
        singer:[],//所有歌手
        songList:[],//所有歌单

        consumerSex:{     //根据性别划分比例
            columns:['性别','总数'],  //有哪些类
            rows:[
                   {'性别':'男','总数':0},      
                   {'性别':'女','总数':0}         //哪行

                ],
               
        },
        options:{
            color:['#14b7ed','#902']
        },
        songListStyle:{     //按风格划分
            columns:['风格','总数'],  //有哪些类
            rows:[
                   {'风格':'轻音乐','总数':0},
                   {'风格':'重音乐','总数':0},
                   {'风格':'日韩','总数':0},
                   {'风格':'华语','总数':0},
                   {'风格':'粤语','总数':0},
                   {'风格':'BGM','总数':0},
                   {'风格':'流行乐','总数':0}          
                      ]
        },

        singerSex:{     //根据性别划分比例
            columns:['性别','总数'],  //有哪些类
            rows:[
                   {'性别':'男','总数':0},      
                   {'性别':'女','总数':0},         //哪行
                   {'性别':'组合','总数':0},
                   {'性别':'不明','总数':0}

                ]
               
        },
        nationality:{     //按风格划分
            columns:['地区','总数'],  //有哪些类
            rows:[
                   {'地区':'中国','总数':0},
                   {'地区':'韩国','总数':0},
                   {'地区':'日本','总数':0},
                   {'地区':'英国','总数':0},
                   {'地区':'法国','总数':0},
                   {'地区':'西班牙','总数':0},
                   {'地区':'美国','总数':0},
                   {'地区':'印度','总数':0},
                  
                      ]
        },
    }
   },

   mounted(){
   this.getConsumer(),
   this.getSong(),
   this.getSinger(),
   this.getSongList()
   },
   methods: {
    getConsumer(){
    getAllConsumer(). //查询用户
    then(res=>{
        this.consumerCount=res.length;
        this.consumer=res;
        this.consumerSex.rows[0]['总数']=this.getSex(1,this.consumer)
        this.consumerSex.rows[1]['总数']=this.getSex(0,this.consumer)
         })
        },
    getSex(sex,consumer){
        let count=0;
      for(let item of consumer){
        if (sex==item.sex) {
            count++;
        }
      }
      return count;
    },

    getSong(){
        allSong().   //查询歌曲
        then(res=>{
            this.songCount=res.length;
            this.song=res;
        })
    },
    getSinger(){     //查询歌手
        getAllSinger().
        then(res=>{
            this.singerCount=res.length;
            this.singer=res;
            this.singerSex.rows[0]['总数']=this.getSingerSex(1,this.singer);
            this.singerSex.rows[1]['总数']=this.getSingerSex(0,this.singer);
            this.singerSex.rows[2]['总数']=this.getSingerSex(2,this.singer);
            this.singerSex.rows[3]['总数']=this.getSingerSex(3,this.singer);
            for(let item of res){
                this.getlocation(item.location);
            }
            
        })
    },
    getlocation(location){   //根据国籍查询
   for(let item of this.nationality.rows){
    if(location.includes(item['地区'])){
        item['总数']++;
    }
   }
    },
    getSingerSex(sex,singer){
       let countt=0;
     for(let item of singer){
        if(sex==item.sex){
            countt++;
        }
     }
     return countt;
    },
    getSongList(){   //歌单数量
        getAllSongList().
        then(res=>{
            this.songList=res;
            this.SongListCount=res.length;
            for(let item of res){
                this.getStyle(item.style);
            }
        })
    },
    getStyle(style){
      for(let item of this.songListStyle.rows){
        if (style.includes(item['风格'])) {
            item['总数']++;
        }
      }
    }
 }
   
}
</script>
<style scoped>
.grid-content{
 display: flex;
 align-items: center;
 height: 50px;

}
.grid-conton{
    flex: 1;
    text-align: center;
    font-size: 14px;
    color: darkgray;


    
}
.num-one{
    font-size: 20px;
    font-weight: bold;

}
</style>