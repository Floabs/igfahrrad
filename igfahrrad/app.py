from flask import Flask, render_template, request, jsonify
import os
import json
import markdown2
import webbrowser
from threading import Timer
import shutil

app = Flask(__name__)
UPLOAD_FOLDER = 'static/uploads'
BIKE_DATA_FILE = 'bikes.json'
BIKE_CONTENT_FOLDER = 'content/neurader'

# Ensure the upload folder exists
if not os.path.exists(UPLOAD_FOLDER):
    os.makedirs(UPLOAD_FOLDER)

# Ensure the bike content folder exists
if not os.path.exists(BIKE_CONTENT_FOLDER):
    os.makedirs(BIKE_CONTENT_FOLDER)

@app.route('/')
def upload_form():
    bikes = load_bike_data()
    return render_template('upload.html', bikes=bikes)

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
        bike_data = load_bike_data()

        # Add new bike data
        bike_data.append({
            'name': bike_name,
            'description': bike_description,
            'price': bike_price,
            'type': bike_type,
            'image': image_path
        })

        # Save updated bike data
        save_bike_data(bike_data)

        # Generate markdown for the new bike
        generate_markdown(bike_data)

        return 'File uploaded and bike data updated successfully'

@app.route('/delete/<bike_name>', methods=['POST'])
def delete_bike(bike_name):
    # Load existing bike data
    bike_data = load_bike_data()

    # Find the bike to delete
    bike_to_delete = None
    for bike in bike_data:
        if bike['name'] == bike_name:
            bike_to_delete = bike
            break

    if bike_to_delete:
        # Remove the bike from the list
        bike_data.remove(bike_to_delete)

        # Save updated bike data
        save_bike_data(bike_data)

        # Delete the bike folder
        bike_folder = os.path.join(BIKE_CONTENT_FOLDER, bike_name.replace(' ', '_').lower())
        if os.path.exists(bike_folder):
            shutil.rmtree(bike_folder)

        # Regenerate the index
        generate_markdown(bike_data)

        return jsonify(success=True)
    else:
        return jsonify(success=False), 404

def load_bike_data():
    if os.path.exists(BIKE_DATA_FILE):
        with open(BIKE_DATA_FILE, 'r') as file:
            return json.load(file)
    else:
        return []

def save_bike_data(bike_data):
    with open(BIKE_DATA_FILE, 'w') as file:
        json.dump(bike_data, file)

def generate_markdown(bike_data):
    for bike in bike_data:
        bike_folder = os.path.join(BIKE_CONTENT_FOLDER, bike['name'].replace(' ', '_').lower())
        if not os.path.exists(bike_folder):
            os.makedirs(bike_folder)

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

{{{{< absurlimg src="./uploads/{os.path.basename(bike['image'])}" alt="{bike['name']}" style="width: 100%;" >}}}}

**Price:** {bike['price']}
**Type:** {bike['type']}
"""

        md_filename = os.path.join(bike_folder, "_index.md")
        with open(md_filename, 'w') as md_file:
            md_file.write(md_content)

    # Generate _index.md for neurader
    index_content = "---\ntitle: \"Neurader\"\n---\n\n## Neurader\n\n<div class=\"bikes-container\" style=\"display: flex; flex-wrap: wrap; gap: 20px;\">\n"
    for bike in bike_data:
        index_content += f"""
<div class="bike-preview" style="flex: 0 0 calc(50% - 20px); box-sizing: border-box;">
    <h2>{bike['name']}</h2>
    {{{{< absurlimg src="./uploads/{os.path.basename(bike['image'])}" alt="{bike['name']}" style="width: 100%; height: auto;" >}}}}
    <p>{bike['description']}</p>
    <p><strong>Price:</strong> {bike['price']}</p>
    <p><strong>Type:</strong> {bike['type']}</p>
    {{{{< absurllink href="./neurader/{bike['name'].replace(' ', '_').lower()}" style="" >}}}}Read more{{{{< /absurllink >}}}}
</div>
"""
    index_content += "\n</div>"

    with open(os.path.join(BIKE_CONTENT_FOLDER, "_index.md"), 'w') as index_file:
        index_file.write(index_content)

def open_browser():
    webbrowser.open_new('http://127.0.0.1:5000/')

if __name__ == "__main__":
    Timer(1, open_browser).start()
    app.run(debug=True)
