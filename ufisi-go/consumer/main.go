package consumer

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
)

/**
 * Inicia el consumidor y lo suscribe al tópico test.
 *
 * @return void
 */
func StartKafka() {
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
		fmt.Println("Message from Inventario : ", string(m.Value))
		fmt.Println("Save on inventary Database!")
	}

}
