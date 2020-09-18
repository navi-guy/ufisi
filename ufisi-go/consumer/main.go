package consumer

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
	producer "ufisi-go/producer"
)

/**
 * Inicia el consumidor y lo suscribe al tópico test.
 *
 * @return void
 */
func StartKafkaConsumer() {
	conf := kafka.ReaderConfig{
		Brokers:  []string{"ec2-3-85-41-237.compute-1.amazonaws.com:9092"},
		Topic:    "facturacion", 
		MaxBytes: 10,
	}

	reader := kafka.NewReader(conf)

	for {
		m, err := reader.ReadMessage(context.Background())
		if err != nil {
			fmt.Println("Some error occured", err)
			continue
		}
		message := string(m.Value)
		fmt.Println("Message from Inventario : ", message)
		go producer.StartKafkaProducer("cuentasPorCobrar", message);
		fmt.Println("Save on inventary Database!")
	}

}
