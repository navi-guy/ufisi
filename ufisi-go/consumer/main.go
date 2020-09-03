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
		Brokers:  []string{"localhost:9092"},
		Topic:    "test", //inventario
		GroupID:  "g1",
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
