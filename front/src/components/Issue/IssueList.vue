<template>
  <div class='container'>
    <md-button class="md-raised" v-on:click="clickIssueCreate()">Create Issue</md-button>
    <input v-model='searchQuery' class='search-box' placeholder='Search for issues...'>
    <div class='timeline' v-if='anyIssue()'>
      <div v-for='(dateWithIssues, date) in searchIssues'>
        <p v-if='dateWithIssues.length > 0' class='date'>{{ date }}</p>
        <div v-for='issue in dateWithIssues' class='issue'>
          <span class='dot'></span>
          <IssueItem :issue="issue"></IssueItem>
        </div>
      </div>
    </div>
    <p v-else>No issues found.</p>
  </div>
</template>

<script>
  import IssueItem from './IssueItem.vue'

  export default {
    components: {IssueItem},
    name: 'IssueList',
    computed: {
      searchIssues () {
        let searchRegex = new RegExp(this.searchQuery, 'i')
        // let searchedObj = {}

        if (this.searchQuery === '') {
          return this.issues
        }
        // for (let date in this.datesIssues.)
        for (let date in this.issues) {
          // searchedObj[date] = this.issues[date].filter((issue) => {
          this.searchedIssues[date] = this.issues[date].filter((issue) => {
            return searchRegex.test(issue.title) ||
              searchRegex.test(issue.content) ||
              searchRegex.test(issue.createdAt) ||
              searchRegex.test(date)
          })
        }
        return this.searchedIssues
        // return searchedObj
      }
    },
    methods: {
      anyIssue: function () {
        return this.countAllIssues() > 0
      },
      countAllIssues: function () {
        let count = 0
        for (let date in this.searchIssues) {
          count += this.searchIssues[date].length
        }
        return count
      },
      clickIssueCreate: function () {
        const params = {
          projectId: this.$route.params.projectId
        }
        this.$router.push({name: 'IssueCreateTemplate', params: params})
      }
    },
    data: function () {
      return {
        issues: {},
        searchedIssues: {},
        searchQuery: '',
        searchQuhttps: ''
      }
    },
    created: function () {
      let projectId = this.$route.params.projectId
      const baseUrl = '/api/projects/' + projectId + '/issues'
      this.axios.get(baseUrl)
        .then((result) => {
          console.log(result)
          this.issues = result.data
        })
      // this.issues = {
      //   'September, 2016': [
      //     {
      //       'id': 1,
      //       'title': 'Five',
      //       'slug': 'five',
      //       'teaser': 'five',
      //       'createdAt': '30.09.2016.'
      //     },
      //     {
      //       'id': 2,
      //       'title': 'Four',
      //       'slug': 'four',
      //       'teaser': 'four',
      //       'createdAt': '15.09.2016.'
      //     }
      //   ]
      // }
    }
  }
</script>

<style scoped>
  #issue_list {
    width: 50%;
    margin: 0 auto;
  }

  * {
    outline: none;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
  }

  .container {
    margin: 0 auto;
    width: 100%;
    max-width: 500px;
  }

  .container .search-box {
    padding: 10px;
    margin: 20px 0;
    border: 1px solid black;
    border-radius: 5px;
  }

  .container .timeline {
    position: relative;
    border-left: 1px solid black;
  }

  .container .timeline .date {
    display: inline-block;
    border: 1px solid black;
    border-radius: 5px;
    padding: 5px;
    position: relative;
    left: 15px;
    margin: 15px 0;
  }

  .container .timeline .date:before {
    content: "";
    position: absolute;
    top: 50%;
    left: -16px;
    border: 1px solid black;
    width: 14px;
  }

  .container .timeline .issue {
    position: relative;
    left: 20px;
    -webkit-box-shadow: 0px 5px 10px 0px rgba(0, 0, 0, 0.2);
    box-shadow: 0px 5px 10px 0px rgba(0, 0, 0, 0.2);
    border-radius: 5px;
    padding: 10px;
    margin: 10px 0;
  }

  .container .timeline .issue:hover {
    -webkit-box-shadow: 0px 5px 10px 0px rgba(0, 0, 0, 0.4);
    box-shadow: 0px 5px 10px 0px rgba(0, 0, 0, 0.4);
  }

  .container .timeline .issue a {
    color: green;
    text-decoration: none;
  }

  .container .timeline .issue .issue-date {
    font-weight: 300;
    font-size: 14px;
  }

  .container .timeline .issue .dot {
    display: block;
    position: absolute;
    width: 10px;
    height: 10px;
    border-radius: 50%;
    background: green;
    left: -25.5px;
    top: calc(50% - 5px);
  }
</style>
