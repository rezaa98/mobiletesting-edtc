import xml.etree.ElementTree as ET
import sys

tree = ET.parse(sys.argv[1])
root = tree.getroot()

for node in root.iter('node'):
    text = node.get('text', '')
    res_id = node.get('resource-id', '')
    if text and ('Beli' in text or 'Keranjang' in text or 'Tambah' in text or 'Rp' in text):
        print(f"[{res_id}] {text}")
