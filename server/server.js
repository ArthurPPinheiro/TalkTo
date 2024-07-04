const express = require('express');
const http = require('http');
const socketIo = require('socket.io');
const cors = require('cors');

const app = express();
app.use(cors());

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header('Access-Control-Allow-Methods', 'DELETE, PUT, GET, POST');
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
 });

const server = http.createServer(app);
const io = socketIo(server);

app.get('/', (req, res) => {
    res.send('Servidor Socket.IO está funcionando');
});

io.on('connection', (socket) => {
    console.log('Novo cliente conectado');

    socket.on('sendMessage', (message) => {
        console.log('Mensagem recebida: ', message);
        io.emit('receiveMessage', message);
    });

    socket.on('sendImage', (message) => {
        console.log('Imagem recebida: ', message);
        io.emit('receiveImage', message);
    });

    socket.on('disconnect', () => {
        console.log('Cliente desconectado');
    });
});

io.engine.on("connection_error", (err) => {
    console.log("err.req", err.req);      // the request object
    console.log("err.code", err.code);     // the error code, for example 1
    console.log("err.message", err.message);  // the error message, for example "Session ID unknown"
    console.log("err.context", err.context);  // some additional error context
  });
  
  
  

const PORT = process.env.PORT || 3000;
server.listen(PORT, () => console.log(`Servidor rodando na porta ${PORT}`));
