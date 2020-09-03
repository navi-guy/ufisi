package db

import (
	"database/sql"

	_ "github.com/go-sql-driver/mysql" // '_' significa q no se usará la variable
)

const user = "deus"
const password = "deus"
const server = "127.0.0.1"
const port = "3306"

/**
 * Crea una BD si no existe y crea la tabla facturas.
 * @param  name string       Nombre de la base de datos
 * @return void
 */
func CreateDatabase(name string) {

	db, err := sql.Open("mysql", user+":"+password+"@tcp("+server+":"+port+")/")
	if err != nil {
		panic(err)
	}
	defer db.Close()

	_, err = db.Exec("CREATE DATABASE IF NOT EXISTS " + name)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec("USE " + name)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec(`CREATE TABLE IF NOT EXISTS factura(
   			id_factura int NOT NULL AUTO_INCREMENT, 
			id_orden int, 
			codigo_factura varchar(30), 
			fecha date,
			total float,
			PRIMARY KEY (id_factura));`)
	if err != nil {
		panic(err)
	}
}
