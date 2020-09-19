package producer

import (
	"context"
	"fmt"

	"github.com/segmentio/kafka-go"
)

// StartKafkaProducer produce un msge
func StartKafkaProducer(topic string, message string) {
	w := kafka.NewWriter(kafka.WriterConfig{
		Brokers:  []string{"ec2-3-85-41-237.compute-1.amazonaws.com:9092"},
		Topic:    topic,
		Balancer: &kafka.LeastBytes{},
	})

	w.WriteMessages(context.Background(),
		kafka.Message{
			Value: []byte(message),
		},
	)
	fmt.Println("Enviado!")
	w.Close()
}
