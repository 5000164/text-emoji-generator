const {contextBridge, ipcRenderer} = require("electron");
contextBridge.exposeInMainWorld("electron", {
  save: (data) => {
    ipcRenderer.send("save", data);
  }
});
