import xml.etree.ElementTree as ET
import sys

tree = ET.parse(sys.argv[1])
root = tree.getroot()

for node in root.iter('node'):
    bounds = node.get('bounds', '')
    if bounds:
        # e.g., [0,2000][1080,2400]
        try:
            y1 = int(bounds.split(',')[1].split(']')[0])
            if y1 > 2000:
                print(f"[{node.get('resource-id', '')}] {node.get('class', '')} {node.get('text', '')} {bounds}")
        except:
            pass
