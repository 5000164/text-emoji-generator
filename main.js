const fs = require('fs').promises
const path = require('path')
const url = require('url')
const {app, BrowserWindow, Menu, ipcMain, dialog} = require('electron')

let win

function createWindow() {
  win = new BrowserWindow({
    width: 500,
    height: 500,
    titleBarStyle: 'hidden',
    webPreferences: {
      preload: __dirname + '/preload.js'
    },
  })

  const pathname = (process.env.TEXT_EMOJI_GENERATOR_ENV === 'development') ? path.join(__dirname, 'index-dev.html') : path.join(__dirname, 'index.html')

  win.loadURL(url.format({
    pathname: pathname,
    protocol: 'file:',
    slashes: true
  }))

  if (process.env.TEXT_EMOJI_GENERATOR_ENV === 'development') {
    win.webContents.openDevTools()
  }

  win.on('closed', () => {
    win = null
  })

  Menu.setApplicationMenu(Menu.buildFromTemplate([
    {
      label: app.getName(),
      submenu: [
        {role: 'about'},
        {type: 'separator'},
        {role: 'services', submenu: []},
        {type: 'separator'},
        {role: 'hide'},
        {role: 'hideothers'},
        {role: 'unhide'},
        {type: 'separator'},
        {role: 'quit'}
      ]
    },
    {
      label: 'File',
      submenu: [
        {role: 'close'}
      ]
    },
    {
      label: 'Edit',
      submenu: [
        {role: 'undo'},
        {role: 'redo'},
        {type: 'separator'},
        {role: 'cut'},
        {role: 'copy'},
        {role: 'paste'},
        {role: 'pasteandmatchstyle'},
        {role: 'delete'},
        {role: 'selectall'}
      ]
    },
    {
      label: 'View',
      submenu: [
        {role: 'reload'},
        {type: 'separator'},
        {role: 'togglefullscreen'},
        {type: 'separator'},
        {role: 'resetzoom'},
        {role: 'zoomin'},
        {role: 'zoomout'},
        {type: 'separator'},
        {role: 'toggledevtools'}
      ]
    },
    {
      role: 'window',
      submenu: [
        {role: 'minimize'},
        {role: 'zoom'},
        {type: 'separator'},
        {role: 'front'}
      ]
    },
    {
      role: 'help',
      submenu: [
        {
          label: 'Learn More',
          click() {
            require('electron').shell.openExternal('https://github.com/5000164/text-emoji-generator')
          }
        },
        {
          label: 'License and Open Source Notices',
          click() {
            require('electron').shell.openExternal('https://github.com/5000164/text-emoji-generator/blob/master/LICENSE-THIRDPARTY.md')
          }
        }
      ]
    }
  ]))
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

ipcMain.on("save", async (event, {fileName, data}) => {
  const {canceled, filePath} = await dialog.showSaveDialog(null, {
    defaultPath: `${fileName}.png`
  })
  if (!canceled) {
    await fs.writeFile(filePath, data, {encoding: "base64"})
  }
})
