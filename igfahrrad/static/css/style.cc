html, body {
  height: 100%;
  margin: 0;
}

#content {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
}

#main {
  flex: 1;
}

footer {
  background-color: #000;
  color: #fff;
  text-align: center;
  padding: 1em 0;
}

.top-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #000;
  color: #fff;
  padding: 0.5em 1em;
}

.social-links a,
.contact-info a {
  color: #fff;
  margin-right: 0.5em;
  text-decoration: none;
}

.contact-info {
  display: flex;
  align-items: center;
}

.contact-info span {
  margin: 0 0.5em;
}

.social-links a:hover,
.contact-info a:hover {
  text-decoration: underline;
}

table {
  border-collapse: collapse;
  width: 100%;
  margin: 1rem 0;
}

table th, table td {
  border: 1px solid #ccc;
  padding: 0.6rem;
  text-align: left;
}

table th {
  background-color: #f8f8f8;
  font-weight: 600;
}

.my-table-wrapper table {
  /* Make the table span full width, remove double borders */
  width: 100%;
  border-collapse: collapse;
  margin: 1.5rem 0;
  font-family: "Helvetica Neue", Arial, sans-serif;
  font-size: 15px;
  color: #333;
}

.my-table-wrapper thead th {
  background-color: #f4f4f4; /* Light gray header background */
  font-weight: 700;
}

.my-table-wrapper th,
.my-table-wrapper td {
  border: 1px solid #ddd;
  padding: 0.75em 1em;
  vertical-align: middle;
}

/* Zebra striping for table rows */
.my-table-wrapper tbody tr:nth-child(even) {
  background-color: #fafafa;
}

/* Align numeric columns to the right if desired.
   This example assumes columns 2+ are numeric. 
   You can adjust nth-child() for your exact table structure. */
.my-table-wrapper td:nth-child(n+2) {
  text-align: right;
}

