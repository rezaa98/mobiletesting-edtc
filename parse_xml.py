import xml.etree.ElementTree as ET

tree = ET.parse('checkout_dump2.xml')
root = tree.getroot()

for node in root.iter('node'):
    text = node.get('text', '')
    res_id = node.get('resource-id', '')
    content_desc = node.get('content-desc', '')
    
    if text and ('Rp' in text or 'Asuransi' in text or 'Ongkos' in text or 'Total' in text or 'Kirim' in text):
        print(f"Text: '{text}', Res-ID: '{res_id}', Content-Desc: '{content_desc}'")
