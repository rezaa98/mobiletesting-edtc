import xml.etree.ElementTree as ET
import sys

tree = ET.parse(sys.argv[1])
root = tree.getroot()

for node in root.iter('node'):
    text = node.get('text', '')
    if 'Rp' in text:
        print(f"[{node.get('class', '')}] text='{text}' content-desc='{node.get('content-desc', '')}' bounds='{node.get('bounds', '')}' resource-id='{node.get('resource-id', '')}'")
