package main

import (
	"encoding/json"
	"fmt"
	"ufisi-go/db"
	"ufisi-go/model"
	// "ufisi-go/model"
	// "time"
	// appKafka "ufisi-go/consumer"
)

func main() {

	//productos := "{\"products\": [{\"id\":\"21\",\"nombre\":\"papel\",\"precio\":20.5,\"cantidad\":0},{\"id\":\"22\",\"nombre\":\"madera\",\"precio\":10.0,\"cantidad\":0}]}"
	jsonMsge := `{"codigo_factura": "F0008", "total_bruto": 12.5,"igv": 5.2, "fecha": "2020-09-16", "datox": "adsasda"}`
	fact := model.Factura{}
	json.Unmarshal([]byte(jsonMsge), &fact)
	fmt.Println("Factura = ", fact)
	idFactura := StoreFactura(fact)
	fmt.Println(idFactura)
	fmt.Println("Insertado!")
	//db.CreateDatabase("facturacion_db")
	//fmt.Println("BD facturacion_db creada correctamente")
	//go appKafka.StartKafka()
	//fmt.Println("Kafka has been started...")
	//time.Sleep(10 * time.Minute)
}

// StoreFactura insert on database the facturas and item_factura
func StoreFactura(f model.Factura) (id int64) {
	db := db.Conn()
	stmt, err := db.Prepare("INSERT into facturas(codigo_factura,total_bruto,igv,fecha) values(?,?,?,?)")
	checkErr(err)
	res, err := stmt.Exec(f.CodigoFactura, f.TotalBruto, f.Igv, f.Fecha)
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
