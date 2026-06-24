import xml.etree.ElementTree as ET
import sys

tree = ET.parse(sys.argv[1])
root = tree.getroot()

for node in root.iter('node'):
    text = node.get('text', '')
    res_id = node.get('resource-id', '')
    bounds = node.get('bounds', '')
    if text and '10.00 - 10.59' in text:
        print(f"[{res_id}] {text} {bounds}")
