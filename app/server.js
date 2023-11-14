const net = require('net');

const server = net.createServer((socket) => {
    console.log('Cliente conectado.');

    // Evento de recebimento de dados do cliente
    socket.on('data', (data) => {
        console.log(`Recebido: ${data}`);

        // Enviar a mensagem de volta para o cliente
        socket.write(`Servidor: ${data}`);
    });

    // Evento de fechamento da conexão com o cliente
    socket.on('close', () => {
        console.log('Cliente desconectado.');
    });
});

const PORT = 3000;
server.listen(PORT, () => {
    console.log(`Servidor escutando na porta ${PORT}`);
});
