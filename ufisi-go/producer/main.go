package main

import (
	"context"
	"fmt"

	"github.com/segmentio/kafka-go"
)

func StartKafkaProducer() {
	w := kafka.NewWriter(kafka.WriterConfig{
		Brokers:  []string{"localhost:9092"},
		Topic:    "mytopic",
		Balancer: &kafka.LeastBytes{},
	})

	w.WriteMessages(context.Background(),
		kafka.Message{
			Key:   []byte("Key-A"),
			Value: []byte("Hello World!"),
		},
		kafka.Message{
			Key:   []byte("Key-B"),
			Value: []byte("One!"),
		},
		kafka.Message{
			Key:   []byte("Key-C"),
			Value: []byte("Two!"),
		},
	)
	fmt.Print("Fin Mensajes")
	w.Close()
}

func main() {
	fmt.Println("Comenzando Producer")
	StartKafkaProducer()
}
