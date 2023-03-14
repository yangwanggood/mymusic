<template>
    <div class="search-songs">

        <album-content :songList="listOfSong"></album-content>
    </div>
    
    
</template>

<script>
import {mapGetters} from 'vuex'
import AlbumContent from '../AlbumContent.vue';
import { mixin } from '../../mixins';
export default {
    name: "search-songs",
    components: { AlbumContent },
    mixins:[mixin],
    computed:{
        ...mapGetters([
        'listOfSong'
        ])
            
        
      },
      mounted(){
       this.getSong();
   
      }
    }

</script>

<style lang="scss" scoped>
@import '../../assets/css/search-songs.scss'
</style>