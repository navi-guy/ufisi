package main

import (
	"fmt"
	"time"

	appKafka "test/kafka"
)

func main() {

	fmt.Println("Okay...")
	go appKafka.StartKafka()
	fmt.Println("Kafka has been started...")
	time.Sleep(10 * time.Minute)
}
