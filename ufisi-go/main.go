package main

import (
	"fmt"
	"time"
	"ufisi-go/db"

	appKafka "ufisi-go/consumer"
)

func main() {

	db.CreateDatabase("facturacion_db")
	fmt.Println("BD facturacion_db creada correctamente")
	go appKafka.StartKafkaConsumer()
	fmt.Println("Kafka has been started...")
	time.Sleep(10 * time.Minute)
}
