<template>
  <div>
    <md-empty-state
      md-icon="devices_other"
      :md-label=this.project.title
      :md-description= this.project.description >
    </md-empty-state>
  </div>
</template>


<script>

  export default {
    name : 'ProjectMain',
    props: ['project'],
    created:function() {
      if (this.project == undefined) {
        this.projectId = this.$route.params.projectId
        const baseUrl = '/api/projects/' + this.projectId
        this.axios.get(baseUrl)
          .then((result) => {
            console.log(result)
            this.project = result.data
          })

      }
    }
  }
</script>

<style scoped>
  html, body, #editor {
    margin: 0;
    height: 100%;
    font-family: 'Helvetica Neue', Arial, sans-serif;
    color: #333;
  }

  textarea, #editor div {
    display: inline-block;
    width: 49%;
    height: 100%;
    vertical-align: top;
    box-sizing: border-box;
    padding: 0 20px;
  }

  textarea {
    border: none;
    border-right: 1px solid #ccc;
    resize: none;
    outline: none;
    background-color: #f6f6f6;
    font-size: 14px;
    font-family: 'Monaco', courier, monospace;
    padding: 20px;
  }

  code {
    color: #f66;
  }
</style>
