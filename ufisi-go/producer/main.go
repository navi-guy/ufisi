package producer

import (
	"context"
	"fmt"
	"github.com/segmentio/kafka-go"
)

func StartKafkaProducer() {
	w := kafka.NewWriter(kafka.WriterConfig{
		Brokers:  []string{"ec2-3-85-41-237.compute-1.amazonaws.com:9092"},
		Topic:    "cuentasPorCobrar",
		Balancer: &kafka.LeastBytes{},
	})

	w.WriteMessages(context.Background(),
		kafka.Message{
			Key:   []byte("Key-A"),
			Value: []byte("Hello World!"),
		},
	)
	fmt.Print("Fin Mensajes")
	w.Close()
}

func main() {
	fmt.Println("Comenzando Producer")
	StartKafkaProducer()
}
