const {app, BrowserWindow} = require('electron')
const path = require('path')
const url = require('url')

let win

function createWindow() {
  win = new BrowserWindow({'titleBarStyle': 'hidden', width: 300, height: 300})

  const pathname = (process.env.ELECTRON_ENV === 'development') ? path.join(__dirname, 'index-dev.html') : path.join(__dirname, 'index.html')

  win.loadURL(url.format({
    pathname: pathname,
    protocol: 'file:',
    slashes: true
  }))

  win.on('closed', () => {
    win = null
  })
}

app.on('ready', createWindow)

app.on('window-all-closed', () => {
  if (process.platform !== 'darwin') {
    app.quit()
  }
})

app.on('activate', () => {
  if (win === null) {
    createWindow()
  }
})
