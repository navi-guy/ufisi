from kafka import KafkaConsumer
import json
from producer import sendToFacturacion

def receiveFromOrdenes():
    print("Esperando ....")
    consumer = KafkaConsumer(bootstrap_servers=['ec2-3-85-41-237.compute-1.amazonaws.com:9092'], value_deserializer=lambda m: json.loads(m.decode('ascii')))
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