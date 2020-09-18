package db

import (
	"database/sql"

	_ "github.com/go-sql-driver/mysql" // '_' significa q no se usará la variable
)

const user = "root"
const password = "123456"
const server = "127.0.0.1"
const port = "3306"

/**
 * Crea una BD si no existe y crea la tabla facturas.
 * @param  name string       Nombre de la base de datos
 * @return void
 */
func CreateDatabase(name string) {

	db := Conn()
	defer db.Close()

	_, err := db.Exec("CREATE DATABASE IF NOT EXISTS " + name)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec("USE " + name)
	if err != nil {
		panic(err)
	}
	// add table items_factura
	_, err = db.Exec(`CREATE TABLE IF NOT EXISTS facturas(
		id_factura int NOT NULL AUTO_INCREMENT, 
		id_orden int,
		codigo_factura varchar(30), 
		total_bruto float,
		igv float,
		PRIMARY KEY (id_factura));`)
	if err != nil {
		panic(err)
	}
}

// Conn creates a conexion to database.
func Conn() (db *sql.DB) {
	db, err := sql.Open("mysql", user+":"+password+"@tcp("+server+":"+port+")/")
	if err != nil {
		panic(err)
	}
	_, err = db.Exec("USE facturacion_db")
	if err != nil {
		panic(err)
	}
	return db
}
