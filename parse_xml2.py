import xml.etree.ElementTree as ET
tree = ET.parse('checkout_dump2.xml')
root = tree.getroot()
for node in root.iter('node'):
    text = node.get('text', '')
    if text:
        print(text)
