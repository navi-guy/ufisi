const send = (producer, topicName, accountReceivable) => {
    const message = {
        estado: 0,
        message: `Su pedido se ha registrado con exito!.\n La fecha del pago esta programada para el dia ${accountReceivable.paymentDay}`
    }

    const data = [
        { topic: topicName, messages: JSON.stringify(message) }
    ];
    console.log(data);
    producer.send(data, function (err, resp) {
        console.log(resp);
    });
    producer.on('error', function (err) {
        console.log(err);
    });
}

module.exports = send;