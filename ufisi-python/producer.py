# Importando paquetes necesarios
from kafka import KafkaProducer
import json

def sendToFacturacion(msge_to_facturacion=""):
    # Mi productor
    producerPy = KafkaProducer(bootstrap_servers =['127.0.0.1:9092'],
                    value_serializer=lambda v: json.dumps(v).encode('utf-8'), 
                    api_version=(0,10))
    producerPy.send("test", msge_to_facturacion)
