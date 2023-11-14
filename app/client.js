const net = require('net');

const client = new net.Socket();

client.connect(3000, '127.0.0.1', () => {
    console.log('Conectado ao servidor.');

    // Enviar uma mensagem para o servidor
    client.write('Olá, servidor!');
});

// Evento de recebimento de dados do servidor
client.on('data', (data) => {
    console.log(`Recebido do servidor: ${data}`);
});

// Evento de fechamento da conexão com o servidor
client.on('close', () => {
    console.log('Conexão fechada.');
});
