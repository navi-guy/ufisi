package main

import (
	"fmt"
	"time"
	appKafka "ufisi-go/consumer"
	"ufisi-go/db"
)

type User struct {
	IDFactura     int     `json:"id_factura"`
	IDOrden       int     `json:"id_orden"`
	CodigoFactura string  `json:"codigo_factura"`
	Fecha         string  `json:"fecha"`
	Total         float32 `json:"total"`
}

func main() {

	db.CreateDatabase("facturacion")
	fmt.Println("BD creada correctamente")
	go appKafka.StartKafka()
	fmt.Println("Kafka has been started...")
	time.Sleep(10 * time.Minute)
}
