from kafka import KafkaConsumer
import json
from producer import sendToFacturacion
from config import config
import psycopg2

params = config()
# connect to the PostgreSQL server
mi_conexion = psycopg2.connect(**params)
mi_cursor = mi_conexion.cursor()

def receiveFromOrdenes():
    print("Esperando ....")
    consumer = KafkaConsumer(bootstrap_servers=['ec2-3-85-41-237.compute-1.amazonaws.com:9092'], value_deserializer=lambda m: json.loads(m.decode('ascii')))
    consumer.subscribe(['orden'])
    for msg in consumer:
        print("Recibido del Módulo de Procesamiento de ordenes: ")
        print(msg)
        msgToFacturacion = msg.value
        for i in msgToFacturacion["productos"]:
            verificar_stock(i)
        msgToFacturacion = crear_orden(msgToFacturacion) 
        print("Reservado!")
        sendToFacturacion(msgToFacturacion)
        print("Msge enviado a facturación!")
        print("Esperando ....")      

def verificar_stock(i):
    mensaje = ""
    id_producto = i["codigo_producto"]
    cantidad_solicitada = i["cantidad"]
    id_producto = "'" + id_producto + "'"
    cantidad_solicitada_str = str(cantidad_solicitada)
    mi_cursor.execute('SELECT stock FROM PRODUCTOS WHERE id_producto = ' + id_producto)
    stock_producto = mi_cursor.fetchall()
    stock_producto = stock_producto[0]
    stock_producto = stock_producto[0]
    if stock_producto>=cantidad_solicitada:
        mi_cursor.execute("UPDATE PRODUCTOS SET stock= stock-" + cantidad_solicitada_str + " WHERE id_producto = " + id_producto)
        mi_conexion.commit()
    else:
        print("No hay stock")   
        mensaje = "No hay stock para el producto " + i["nombre"]
        print("Enviar a modulo de Benjas")
        #sendToFacturacion("{'estado':'0','mensaje':'" + mensaje + "'}")

def crear_orden(msg):
    mi_cursor.execute("INSERT INTO ORDEN (id_cliente) VALUES(" + msg["id_cliente"] + ")")
    mi_conexion.commit()
    mi_cursor.execute("SELECT id_orden FROM ORDEN ORDER BY id_orden DESC LIMIT 1")
    ultimo_id_orden = mi_cursor.fetchall()
    ultimo_id_orden = ultimo_id_orden[0]
    ultimo_id_orden = ultimo_id_orden[0]
    for i in msg["productos"]:
        str_id_producto = str(i["codigo_producto"])
        str_cantidad_producto = str(i["cantidad"])        
        mi_cursor.execute("INSERT INTO DETALLE_ORDEN (id_orden, id_producto, cantidad) VALUES(" + str(ultimo_id_orden) + ", "+ str_id_producto + ", "+ str_cantidad_producto +")")
        mi_conexion.commit()
    msg["id_orden"] = ultimo_id_orden # AQUI LO AGREGO NO MAS
    return msg