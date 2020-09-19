package consumer

import (
	"context"
	"encoding/json"
	"fmt"
	"math"
	"math/rand"
	"ufisi-go/db"
	"ufisi-go/model"
	producer "ufisi-go/producer"

	"github.com/segmentio/kafka-go"
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
		//jsonMsge := `{"id_orden": "1", "codigo_factura": "F0008", "total_bruto": 12.5,"igv": 5.2, "datox": "adsasda"}`
		jsonMsge := message
		fact := model.Factura{}
		json.Unmarshal([]byte(jsonMsge), &fact)
		numberRandomString := randomString(5)
		fact.CodigoFactura = "FA-00" + numberRandomString
		totalBruto := fact.TotalBruto * 0.18
		fact.Igv = math.Round(totalBruto*100) / 100
		fact.TotalNeto = fact.TotalBruto + fact.Igv
		//fmt.Println("Factura = ", fact)
		idFactura := StoreFactura(fact)
		fmt.Println("Save on inventary Database!")
		fact.IDFactura = idFactura
		msgeToCtasXCobrar, err := json.Marshal(fact)
		if err != nil {
			fmt.Println(err)
			return
		}
		fmt.Println("Mensaje a Ctas x cobrar:")
		fmt.Println(string(msgeToCtasXCobrar))
		producer.StartKafkaProducer("cuentasPorCobrar", string(msgeToCtasXCobrar))

	}
}

// StoreFactura insert on database the facturas and item_factura
func StoreFactura(f model.Factura) (id int64) {
	db := db.Conn()
	_, err := db.Exec("USE facturacion_db")
	if err != nil {
		panic(err)
	}

	stmt, err := db.Prepare("INSERT into facturas(id_orden,codigo_factura,total_bruto,igv) values(?,?,?,?)")
	checkErr(err)
	res, err := stmt.Exec(f.IDOrden, f.CodigoFactura, f.TotalBruto, f.Igv)
	checkErr(err)
	id, err = res.LastInsertId()
	checkErr(err)
	return id
}

func checkErr(err error) {
	if err != nil {
		panic(err)
	}
}
func randomString(l int) string {
	bytes := make([]byte, l)
	for i := 0; i < l; i++ {
		bytes[i] = byte(randInt(65, 90))
	}
	return string(bytes)
}

func randInt(min int, max int) int {
	return min + rand.Intn(max-min)
}
