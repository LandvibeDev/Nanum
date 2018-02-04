<template>
  <!--<div>-->
    <!--<md-field>-->
      <!--<label>Content</label>-->
      <!--<md-textarea v-model="content" required></md-textarea>-->
    <!--</md-field>-->
    <!--<md-button class="md-raised" v-on:click="createIssueComment()">new</md-button>-->
  <!--</div>-->
  <!--<div id="app" :class="[showMenu ? 'show-menu' : '', 'app']">-->
  <div id="app" class="app">
    <main>
      <navBar />
      <section>
        <inputer />
        <outputer />
      </section>
    </main>
  </div>
</template>

<script>
  import navBar from './markdown/NavBar.vue'
  import inputer from './markdown/Inputer.vue'
  import outputer from './markdown/Outputer.vue'
  export default {
    name: 'IssueCommentCreate',
    components: {
      navBar,
      inputer,
      outputer
    },
    methods: {
      createIssueComment: function () {
        this.axios.post(this.baseUrl, {
          issueComment: {
            content: this.content.split('\n').join('<br>')
          },
          issueId: this.issueId
        })
          .then((result) => {
            // 부모 컴포넌트의 addIssueComment를 호출하고 data를 전달하여 comment 추가
            this.$emit('addIssueComment', result.data)
            this.content = ''
          })
      }
    },
    data: function () {
      return {
        content: '',
        issueId: this.$route.params.issueId,
        baseUrl: '/api/issue-comments'
      }
    },
    mounted () {
      // this.$store.dispatch('loadCache')
    },
    computed: {
      showMenu () {
        return this.$store.state.showMenu
      }
    }
  }
</script>

<style lang="less">
  @import url('../../../static/markdown.less');
  html,
  body {
    margin: 0;
    padding: 0;
    font-family: Microsoft Yahei, "PingHei", "Helvetica Neue", "Helvetica", "STHeitiSC-Light", "Arial", sans-serif;
    height: 100%;
    /*overflow: hidden;*/
    background-color: #e0e0e0;
  }
  .app {
    /*overflow-x: hidden;*/
    position: relative;
    /*left: -220px;*/
    height: 100%;
    transition: all ease .5s;
    &.show-menu {
      left: 0;
      main {
        section {
          padding-right: 230px;
        }
      }
    }
    main {
      /*margin-left: 220px;*/
      width: 100%;
      height: 100%;
      background-color: #e0e0e0;
      section {
        box-sizing: border-box;
        height: 100%;
        padding: 10px;
        padding-bottom: 55px;
        display: flex; // item이 수평축으로 알아서 정렬됨
        justify-content: center; // 중심축, 교차축에 기준하여 item을 정렬
        transition: all ease .5s;
      }
    }
  }
  *::-webkit-scrollbar {
    display: block; // scroll enable
    width: 5px;
    background: #FAFAFA;
  }
  *::-webkit-scrollbar-thumb {
    border-radius: 5px;
    background: #E0E0E0;
  }
  *::-webkit-scrollbar-thumb:hover {
    background: #BDBDBD;
  }
</style>
