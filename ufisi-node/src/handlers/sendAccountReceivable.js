const send = (producer, topicName, accountReceivable) => {
    const data = [
        { topic: topicName, messages: JSON.stringify(accountReceivable) }
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