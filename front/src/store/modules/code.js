import axios from 'axios'

const state = {
  dragFile: null
}

const getters = {
  dragFile: function (state) {
    return state.dragFile
  }
}

const actions = {
  moveDragFile: function ({commit}, {projectId, fileName, path, newPath, fileType}) {
    let url = '/api/projects/' + projectId + '/moveFiles/' + fileName
    const data = {
      path: path,
      newPath: newPath,
      type: fileType
    }
    return axios.put(url, data)
  }
}

const mutations = {
  setDragFile: function (state, dragFile) {
    state.dragFile = dragFile
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
