const {app, BrowserWindow} = require('electron');

let win;

function createWindow() {
    let serverProcess = require('child_process')
        .spawn('java', ['-jar', app.getAppPath() + '\\app\\terminal-spring-boot.jar', ''] );

    let appUrl = 'http://localhost:8080';

    const openWindow = function () {
        let mainWindow = new BrowserWindow({
            title: 'Demo',
            width: 640,
            height: 480,
            show: false,
        });
        mainWindow.removeMenu();
        mainWindow.maximize();

        mainWindow.loadURL(appUrl);

        mainWindow.on('closed', function () {
            mainWindow = null;
        });

        mainWindow.on('close', function (e) {
            if (serverProcess) {
                e.preventDefault();
                const kill = require('tree-kill');
                kill(serverProcess.pid, 'SIGTERM', function () {
                    console.log('Server process killed');
                    serverProcess = null;
                    mainWindow.close();
                });
            }
        });
    };

    const startUp = function () {
        const requestPromise = require('minimal-request-promise');

        requestPromise.get(appUrl).then(function () {
            console.log('Server started!');
            openWindow();
        }, function () {
            console.log('Waiting for the server start...');

            setTimeout(function () {
                startUp();
            }, 200);
        });
    };

    startUp();
}

app.on('ready', createWindow);

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
        app.quit()
    }
});

app.on('activate', () => {
    if (win === null) {
        createWindow()
    }
});
