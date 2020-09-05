from kafka import KafkaConsumer
import json
from producer import sendToFacturacion

def receiveFromOrdenes():
    print("Esperando ....")
    consumer = KafkaConsumer(value_deserializer=lambda m: json.loads(m.decode('ascii')))
    consumer.subscribe(['inventario'])
    for msg in consumer:
        msgToFacturacion = msg.value 
        print("Recibido del Módulo de Procesamiento de ordenes: ")
        print(msg)
        print("Procesando ....")
        print("Reservado!")
        sendToFacturacion(msgToFacturacion)
        print("Msge enviado a facturación!")
        print("Esperando ....")