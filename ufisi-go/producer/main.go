package producer

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
)

func StartKafkaProducer(topic string, message string) {
		fmt.Print(message)

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
	fmt.Print("Fin Mensajes")
	w.Close()
}

// func main() {
// 	fmt.Println("Comenzando Producer")
// 	StartKafkaProducer()
// }
