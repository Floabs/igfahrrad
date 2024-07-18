from flask import Flask, render_template, request
import os
import json
import markdown2
import webbrowser
from threading import Timer

app = Flask(__name__)
UPLOAD_FOLDER = 'static/uploads'
BIKE_DATA_FILE = 'bikes.json'

# Ensure the upload folder exists
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

@app.route('/')
def upload_form():
    return render_template('upload.html')

@app.route('/upload', methods=['POST'])
def upload_file():
    if request.method == 'POST':
        bike_name = request.form['bike_name']
        bike_description = request.form['bike_description']
        bike_price = request.form['bike_price']
        bike_type = request.form['bike_type']
        bike_image = request.files['bike_image']

        # Save the uploaded image
        image_path = os.path.join(UPLOAD_FOLDER, bike_image.filename)
        bike_image.save(image_path)

        # Load existing bike data
        if os.path.exists(BIKE_DATA_FILE):
            with open(BIKE_DATA_FILE, 'r') as file:
                bike_data = json.load(file)
        else:
            bike_data = []

        # Add new bike data
        bike_data.append({
            'name': bike_name,
            'description': bike_description,
            'price': bike_price,
            'type': bike_type,
            'image': image_path
        })

        # Save updated bike data
        with open(BIKE_DATA_FILE, 'w') as file:
            json.dump(bike_data, file)

        # Generate markdown for the new bike
        generate_markdown(bike_data)

        return 'File uploaded and bike data updated successfully'

def generate_markdown(bike_data):
    # Generate markdown for each individual bike
    for bike in bike_data:
        md_content = f"""
---
title: "{bike['name']}"
description: "{bike['description']}"
price: "{bike['price']}"
type: "{bike['type']}"
date: 2024-07-13T12:00:00Z
---

## {bike['name']}

### {bike['description']}

{{{{< absurlimg src="./uploads/{bike['image'].split('/')[-1]}" alt="{bike['name']}" style="" >}}}}

**Price:** {bike['price']}
**Type:** {bike['type']}
"""
        bike_folder = f"content/neurader/{bike['name'].replace(' ', '_').lower()}"
        if not os.path.exists(bike_folder):
            os.makedirs(bike_folder)
        md_filename = os.path.join(bike_folder, "_index.md")
        with open(md_filename, 'w') as md_file:
            md_file.write(md_content)

    # Generate _index.md for the neurader folder
    index_md_content = """
---
title: "Neurader"
---

## Neurader
"""
    for bike in bike_data:
        index_md_content += f"""
<div class="bike-preview">
    <h2>{bike['name']}</h2>
    {{{{< absurlimg src="./uploads/{bike['image'].split('/')[-1]}" alt="{bike['name']}" style="" >}}}}
    <p>{bike['description']}</p>
    <p><strong>Price:</strong> {bike['price']}</p>
    <p><strong>Type:</strong> {bike['type']}</p>
    {{{{< absurllink href="./neurader/{bike['name'].replace(' ', '_').lower()}" style="" >}}}}Read more{{{{< /absurllink >}}}}
</div>
"""
    with open("content/neurader/_index.md", 'w') as index_md_file:
        index_md_file.write(index_md_content)

def open_browser():
    webbrowser.open_new('http://127.0.0.1:5000/')

if __name__ == "__main__":
    Timer(1, open_browser).start()
    app.run(debug=True)
