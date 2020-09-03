# Proyecto Sistemas Distribuidos - FISI UTILES
Proyecto del curso Sistemas Distribuidos. Orquestación de procesos de negocio. Demostrar la aplicabilidad de la utilización de la Tecnología de Middleware Orientado a Mensajes para soportar la ejecución sincronizada de procesos de negocio

## Orquestación de procesos de negocio
![Macpato - Página 1](https://user-images.githubusercontent.com/45251599/91544497-710c2e80-e8e5-11ea-8377-97eaa3177e22.png)
### Pre-requisitos 📋
https://kafka.apache.org/downloads

https://git-scm.com/downloads

## Importante 🤬🤬
* Para descargar el repositorio en tu PC, debes descargarlo sobre la carpeta __*C:\Users\YourName\go\src*__ (Ver instalación de Go), **es decir primero descargar Go y luego descargar el repositorio.**
* Las siguientes  instrucciones son para la instalación y configuración de los módulos en **Windows**.

## Golang | Facturación  📠
### Requerimientos e instalación 🔧
1. Instala **Go** en tu PC, a continuación se encuentra el link a la página principal de **Go** para descargar.

https://golang.org/dl/

2. Comprueba tu instalación haciendo un "Hola mundo", dirigete al workspace de go (en Windows es usualmente __*C:\Users\YourName\go*__ ).

    En la carpeta  ```go\src``` crea un archivo con nombre _hello.go_ y coloca el siguiente programa en él:

```go
package main

import "fmt"

func main() {
	fmt.Printf("Gaaa\n")
}
```
Corre el programa desde la consola de comandos ( go/src ):
```
src> go run hello.go
```
```
Gaaa
```
3. Una vez ya instalado Go, tienes que configurar el archivo __ufisi-go/db/main.go__, configuralo con las credenciales de tu conexión a **Mysql**.

4. Ahora adentro de la carpeta `ufisi-go/`, en la consola de comandos ejecuta lo siguiente para correr el programa principal del módulo de facturación (consumidorxAhora):

`go run main.go`

*Este comando instalará las dependencias automáticamente antes de iniciar el programa.

## Python | Inventario y reserva de ordenes 📦
### Requerimientos e instalación 🔧
1. Tener instalado lo siguiente:

https://www.postgresql.org/download/

https://www.python.org/

_*Asegurate de tener pip instalado_

_**Para el módulo es recomendable tener instalado Python en su versión 3.7 o 3.6_

2. Crear un entorno virtual para el módulo. Puedes hacerlo con virtualenv, instalalo si aun no lo tienes.

i. Instala virtualenv con pip

`pip install virtualenv`

ii.	Dirigete a la carpeta donde descargaste el repositorio y al módulo de python ( C:\Users\YourName\go\src\fisi-tiendas-utiles\ufisi-python ) y abre la consola de cmd. 


iii.	En el cmd, ingresa el siguiente comando, para crear el entorno virtual del módulo:


`virtualenv venv`

*Donde `venv` es el nombre del entorno virtual.

3. Activa el entorno virtual e instala las dependencias.

i.	Después de crear el entorno virtual tienes que activarlo, para eso ejecuta los siguientes comandos:

`cd venv/Scripts`

`activate`

ii.	Después de activar el entorno virtual, tienes instalar las librerías necesarias, ejecuta los siguientes comandos.


```
cd ..
cd ..
pip install -r requirements.txt

``` 
4. Configura la conexión a la base de datos. Ingresa al shell de postgrestql, presiona enter para cada credencial e ingresa con la contraseña que pusiste en la instalación. Crear la base de datos

``` 
CREATE USER navi;
ALTER USER navi WITH PASSWORD 'navi_pass';
CREATE DATABASE inventario OWNER navi;
```
Configura el archivo database.ini con las credenciales con las que creaste la bd.

5. Ahora adentro de la carpeta de __ufisi/python__, en la consola de comandos ejecuta el siguiente comando.
 
```
python main.py
```

*Creará las tablas necesarias.
**Enviará el mensaje con el tópico "test" al módulo de facturación.


## Java | Procesamiento de ordenes 🛒
...
## Javascript | Cuentas por Cobrar 💳
...