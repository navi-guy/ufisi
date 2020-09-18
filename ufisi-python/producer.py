# Importando paquetes necesarios
from kafka import KafkaProducer
import json

def sendToFacturacion(msge_to_facturacion=""):
    # Mi productor
    producerPy = KafkaProducer(bootstrap_servers =['ec2-3-85-41-237.compute-1.amazonaws.com:9092'],
                    value_serializer=lambda v: json.dumps(v).encode('utf-8'), 
                    api_version=(0,10))
    producerPy.send("facturacion", msge_to_facturacion)
