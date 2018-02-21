import axios from 'axios'

const state = {
  dragFile: null,
  modifyingFiles: []
}

const getters = {
  dragFile: function (state) {
    return state.dragFile
  },
  modifyingFiles: function (state) {
    return state.modifyingFiles
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
  },
  getModifyingFiles: function ({commit}, {projectId}) {
    let url = '/api/projects/' + projectId + '/modifyingFiles'
    axios.get(url).then((result) => {
      commit('setModifyingFiles', result.data)
    })
  }
}

const mutations = {
  setDragFile: function (state, dragFile) {
    state.dragFile = dragFile
  },
  addModifyingFile: function (state, file) {
    state.modifyingFiles.push(file)
  },
  removeModifyingFile: function (state, file) {
    state.modifyingFiles.splice(state.modifyingFiles.indexOf(file), 1)
  },
  setModifyingFiles: function (state, files) {
    state.modifyingFiles = files
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
