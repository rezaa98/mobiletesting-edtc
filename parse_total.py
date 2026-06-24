import xml.etree.ElementTree as ET
import sys

tree = ET.parse(sys.argv[1])
root = tree.getroot()

found = False
for node in root.iter('node'):
    text = node.get('text', '')
    if found and 'Rp' in text:
        print(f"Total Amount text found: {text}")
        print(f"Class: {node.get('class', '')}")
        print(f"Resource-id: {node.get('resource-id', '')}")
        break
    if text == 'Total Pembayaran :':
        found = True
